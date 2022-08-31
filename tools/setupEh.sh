#!/usr/bin/env bash

HOSTNAME=${2:-$(hostname -s)}
RG=${1:-$USER-$HOSTNAME}
SUBSCRIPTION=${3:-$(az account show --query id --output tsv)}
PREFIX=${4:-$USER}
LOCATION=${5:-eastus}


mumu3() {
# see https://github.com/naknak-/mumu3 This code implements MurmurHash3's 32-bit hashing function in pure bash.
# this is same as what uniqueString in ARM templates provides.
# this is deterministic and repeatable. TF cannot take varied names as it will destroy and recreate resources 
# every time it runs. This is a workaround to get a unique name for each resource.
  local -r key="${1?usage: mumu key [seed] [fmt]}" len=${#1} mask=$((0xffffffff))
  local h=$(( ${2:-0} )) i=0 k
  while (( i < len )); do
    printf -v k %02x "'${key:(i+3):1}" "'${key:(i+2):1}" "'${key:(i+1):1}" "'${key:(i):1}"
    (( k=0x$k * 0xcc9e2d51 & mask, h=(h ^ ((k "<<" 15 | k>>17)*0x1b873593) ) & mask ))
    (( (i+=4) <= len )) && (( h=( (h "<<" 13 | h>>19) * 5 + 0xe6546b64 ) & mask ))
  done  
  (( h^=len, h^=h>>16, h*=0x85ebca6b, h^=(h&mask)>>13, h=h*0xc2b2ae35 & mask, h^=h>>16 ))
  hash=$h
  [[ "${#@}" -gt 2 && -z "$3" ]] || printf ${3:-"%08x\n"} $h
}

uniqueString=$(mumu3 $PREFIX-$SUBSCRIPTION)

echo "### uniqueString used for globally unique resources: $uniqueString"


az account set --subscription "${SUBSCRIPTION}"

# Create a resource group. Specify a name for the resource group.
#az group create --name "${RG}" --location "${LOCATION}"

# Create an Event Hubs namespace. Specify a name for the Event Hubs namespace.
#az eventhubs namespace create --name "${PREFIX}-${uniqueString}" --resource-group "${RG}" -l "${LOCATION}"

# Create an event hub. Specify a name for the event hub. 
#az eventhubs eventhub create --name "${PREFIX}-eh-${uniqueString}" --resource-group "${RG}" --namespace-name "${PREFIX}-${uniqueString}"


# az eventhubs namespace authorization-rule create --name "reader" \
#                                                  --namespace-name "${PREFIX}-${uniqueString}" \
#                                                  --resource-group "${RG}" \
#                                                  --rights Listen Send

az eventhubs namespace authorization-rule keys list --name "reader" \
                                                    --namespace-name "${PREFIX}-${uniqueString}" \
                                                    --resource-group "${RG}" \