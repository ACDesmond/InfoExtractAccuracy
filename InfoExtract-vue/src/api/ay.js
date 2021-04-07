import request from '@/utils/request'

// 案由信息项
export function getAyInfo() {
    return request({
        url: 'infoextract/ay/info',
        method: 'get',
    })
}

export function addAyInfo(data) {
    return request({
        url: 'infoextract/ay/addinfo',
        method: 'post',
        data
    })
}

export function updateAyInfo(data) {
    return request({
        url: 'infoextract/ay/updateinfo',
        method: 'post',
        data
    })
}

// 案由项
export function getAyList() {
    return request({
        url: 'infoextract/ay/ay',
        method: 'get'
    })
}

export function addAy(data) {
    return request({
        url: 'infoextract/ay/adday',
        method: 'post',
        data
    })
}

export function updateAyItem(data) {
    return request({
        url: 'infoextract/ay/updateay',
        method: 'post',
        data
    })
}

export function deleteAyItem(data) {
    return request({
        url: 'infoextract/ay/deleteay',
        method: 'post',
        data
    })
}

//案由配置
export function ayConfList(user) {
    return request({
        url: 'infoextract/ayconf/list',
        method: 'get',
        params: { user }
    })
}

export function addAyConf(data) {
    return request({
        url: 'infoextract/ayconf/add',
        method: 'post',
        data
    })
}

export function editAyConf(data) {
    return request({
        url: 'infoextract/ayconf/edit',
        method: 'post',
        data
    })
}

export function deleteAyConf(data) {
    return request({
        url: 'infoextract/ayconf/delete',
        method: 'post',
        data
    })
}