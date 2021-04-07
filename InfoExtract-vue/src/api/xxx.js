import request  from "@/utils/request"

export function getXxxList() {
    return request({
        url: 'infoextract/xxx/list',
        method: 'get'
    })
}

export function getShXxxList() {
    return request({
        url: 'infoextract/sh/shlist',
        method: 'get'
    })
}


export function submitCheck(data) {
    return request({
        url: 'infoextract/sh/submit',
        method: 'post',
        data
    })
}

export function addXxx(data) {
    return request({
        url: 'infoextract/xxx/addxxx',
        method: 'post',
        data
    })
}

export function editXxx(data) {
    return request({
        url: 'infoextract/xxx/updatexxx',
        method: 'post',
        data
    })
}

export function deleteXxx(data) {
    return request({
        url: 'infoextract/xxx/deletexxx',
        method: 'post',
        data
    })
}

export function extract(){
    return request({
        url: 'infoextract/xxx/extracttest',
        method: 'get',
    })
}