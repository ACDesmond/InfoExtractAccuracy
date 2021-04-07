<template>
    <div class="app-container">
        <el-button icon="el-icon-edit" type="primary" @click="showAddDialog">添加案由配置</el-button>

        <el-table :data="ayConfList">
            <el-table-column label="ID" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column label="案由配置名称" align="center">
                <template slot-scope="{row}">
                    <el-popover
                        trigger="click"
                        placement="left"
                    >
                        <span>{{ row.xxx }}</span>
                        <!-- 这里先这样展示数据row.xxx，后面换成用表格展示 -->
                        <el-button slot="reference">{{ row.name }}</el-button>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column label="Actions" align="center">
                <template slot-scope="{row, $index}">
                    <el-button size="mini" :disabled="row.disable" type="primary" @click="showEditForm(row, $index)">
                        编辑
                    </el-button>
                    <el-button size="mini"  :disabled="row.disable" type="danger" @click="handleDeleteItem(row,$index)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 用户添加新案由配置的dialog -->
        <el-dialog
            title="添加新案由配置"
            :visible.sync="addFormVisible"    
        >
            <el-form>
                <el-form-item label="案由配置名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="案由类型">
                    <el-select v-model="form.aytype" filterable @change="changeOptions">
                        <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="信息项配置">
                    <dnd-list :list1="list1" :list2="list2" list1-title="已选信息项" list2-title="待选信息项"></dnd-list>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false" type="error">取 消</el-button>
                <el-button @click="handleAdd" type="primary">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 用户编辑某个案由配置的dialog -->
        <el-dialog
            title="编辑案由配置"
            :visible.sync="editFormVisible"
        >
            <el-form>
                <el-form-item label="案由配置名称">
                    <el-input v-model="edit_form.name"></el-input>
                </el-form-item>
                <el-form-item label="案由类型">
                    <el-select v-model="form.aytype" filterable @change="changeOptions">
                        <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="信息项配置">
                    <dnd-list :list1="edit_form.xxxList" :list2="list2" list1-title="已选信息项" list2-title="待选信息项" width="30%" width2="30%" ></dnd-list>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editFormVisible = false" type="error">取 消</el-button>
                <el-button @click="handleEdit" type="primary">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 删除某个案由配置的dialog -->
        <el-dialog
            title="删除案由配置"
            :visible.sync="deleteFormVisible"
        >
            <span>确定要删除“{{ deleteItem.name }}”案由配置吗？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="deleteFormVisible = false">取消</el-button>
                <el-button @click="handleDelete" type="primary">确认</el-button>
            </div>
        </el-dialog>
    </div>
  
</template>

<script>
import DndList from '@/components/DndList'
import { getAyInfo } from '@/api/ay'
import { getXxxList } from "@/api/xxx"
import { addAyConf } from "@/api/ay"
import { ayConfList } from "@/api/ay"
import { editAyConf } from '@/api/ay'
import { deleteAyConf } from '@/api/ay'

import Qs from 'qs'

export default {
    components: { DndList },
    data() {
        return{
            //案由配置数据
            ayConfList: [],
            //案由列表
            ayInfoList: [],
            //信息项列表
            xxxList: [],

            //添加案由配置的dialog的表单数据
            form: {
                //案由配置名称
                name: '',
                //按照案由筛选信息项
                aytype: ''
            },

            //编辑的表单数据
            edit_form: {
                index: '',
                //案由配置的id
                id: '',
                //案由配置名称
                name: '',
                //更新后的信息项列表
                xxxList: []

            },

            //删除的表单数据
            deleteItem: {
                name: '',
                index: null,
                id: null
            },

            //当前用户
            user: this.$store.getters.name,
            role: this.$store.getters.roles,
            
            addFormVisible: false,
            editFormVisible: false,
            deleteFormVisible: false,

            //添加新案由配置dnd-list用到的list
            list1: [],
            list2: []

            //
        }
    },
    created() {
        this.getAyConfData()
        this.getAyInfoData()
        this.getXxxData()
        
    },
    methods: {
        getAyConfData() {
            ayConfList(this.user).then(response => {
                const { page } = response
                //每一项添加一个boolean类型的属性，用来控制Action列是否可操作
                let tmp = page.list
                for(let i = 0 ; i < tmp.length ; ++i){
                    let is_disable = false
                    //如果当前用户不是admin，且这条案由配置是由admin提交的，那么就置为false
                    if(this.role != 'admin' && tmp[i].user == 'admin'){
                        is_disable = true
                    }
                    this.ayConfList.push({
                        id: tmp[i].id,
                        name: tmp[i].name,
                        xxx: tmp[i].xxx,
                        disable: is_disable
                    })
                }
                //disable属性只是为了控制前端button是否可用
                
                // this.ayConfList = page.list
                //todo 暂定不拆分信息项字符串
            })
        },
        getAyInfoData() {
            getAyInfo().then(response => {
                const { page } = response
                this.ayInfoList = page.list
            })
        },
        getXxxData() {
            getXxxList().then(response => {
                const { page } = response
                this.xxxList = page.list
            })
        },
        //改变案由类型时，修改list2下面的信息项列表
        changeOptions(selectVal) {
            for(let i = 0 ; i < this.xxxList.length ; ++i){
                if(this.xxxList[i].aytype == selectVal){
                    console.log('这里是: ' + this.xxxList[i].xxxmc)
                    this.list2.push({
                        id: this.xxxList[i].id,
                        xxxmc: this.xxxList[i].xxxmc
                    })
                }
            }
        },
        showAddDialog() {
            this.list1 = []
            this.list2 = []
            this.form.aytype = ''
            //把当前的list2的信息项的值改为默认案由下的信息项
            for(let i = 0 ; i < this.xxxList.length ; ++i){
                if(this.xxxList[i].aytype == this.form.aytype){
                    this.list2.push({
                        id: this.xxxList[i].id,
                        xxxmc: this.xxxList[i].xxxmc
                    })
                }
            }
            this.addFormVisible = true
        },
        showEditForm(row, index) {
            this.edit_form.index = index
            this.edit_form.id = row.id
            this.edit_form.name = row.name
            this.edit_form.xxxList = []
            this.form.aytype = ''
            this.list2 = []
            let tmp = row.xxx
            let list = tmp.split("\,")
            if(tmp != '') {
                for (let i = 0; i < list.length; i++) {
                    this.edit_form.xxxList.push({
                        xxxmc: list[i]
                    }) 
                }
            }
            
            this.editFormVisible = true

        },
        handleDeleteItem(row, index) {
            this.deleteItem.id = row.id
            this.deleteItem.index = index
            this.deleteItem.name = row.name

            this.deleteFormVisible = true
        },
        handleAdd() {
            // //远程和本地都要添加
            // let len = this.ayConfList.length
            // this.ayConfList.push({
            //     id: len + 1,
            //     user: this.user,
            //     name: this.form.name,
            //     xxx: this.list1.join(",")
            // })

            const xxx = []
            for(let i = 0 ; i < this.list1.length ; ++i){
                xxx.push(this.list1[i].xxxmc)
            }
            Qs.stringify({xxx: this.list1},{arrayFormat: 'repeat'})
            //如果当前角色是admin，那么这个添加操作就被认为是管理员的推荐配置，name属性统一为admin
            if(this.$store.getters.roles == 'admin'){
                this.form.name = 'admin'
            }
            addAyConf({user: this.user, name: this.form.name, xxx: xxx}).then(response => {
                const { code, msg } = response
                if(code == 20000) {
                    this.$notify({
                        title: 'Success',
                        message: '添加成功',
                        type: 'success',
                        duration: 2000
                    })
                     //远程和本地都要添加
                    let len = this.ayConfList.length
                    this.ayConfList.push({
                        id: len + 1,
                        user: this.user,
                        name: this.form.name,
                        xxx: this.list1.join(",")
                    })
                    this.addFormVisible = false
                    this.form.name = null
                    //更新现有案由配置列表 或 重新读取案由配置表 todo
                }else {
                    this.$notify({
                        title: 'Failure',
                        message: '添加失败,' + msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            })
        },
        handleEdit() {
            //远程和本地都要更新
            this.ayConfList[this.edit_form.index].name = this.edit_form.name
            let tmp = []
            for(let i = 0 ; i < this.edit_form.xxxList.length ; ++i) {
                tmp.push(this.edit_form.xxxList[i].xxxmc)
            }
            this.ayConfList[this.edit_form.index].xxx = tmp.join(",")

            let xxx = []
            for(let i = 0 ; i < this.edit_form.xxxList.length ; ++i){
                xxx.push(this.edit_form.xxxList[i].xxxmc)
            }
            Qs.stringify({xxx: this.edit_form.xxxList},{arrayFormat: 'repeat'})
            editAyConf({id: this.edit_form.id, name: this.edit_form.name, xxx: xxx}).then(response => {
                const { code, msg } = response
                if(code == 20000) {
                    this.$notify({
                        title: 'Success',
                        message: '修改成功',
                        type: 'success',
                        duration: 2000
                    })
                    this.editFormVisible = false
                    
                }else {
                    this.$notify({
                        title: 'Failure',
                        message: '修改失败,' + msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            })
        },
        handleDelete() {
            //远程和本地都要删除
            this.ayConfList.splice(this.deleteItem.index, 1)

            deleteAyConf({id: this.deleteItem.id}).then(response => {
                const { code, msg } = response
                if(code == 20000){
                     this.$notify({
                        title: 'Success',
                        message: '删除成功',
                        type: 'success',
                        duration: 2000
                    })
                    this.deleteFormVisible = false
                 }else {
                     this.$notify({
                        title: 'Failure',
                        message: '删除失败,' + msg,
                        type: 'error',
                        duration: 2000
                    })
                 }
            })

        }
    }

}
</script>


<style>

</style>