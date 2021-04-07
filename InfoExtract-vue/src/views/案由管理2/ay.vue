<template>
    <div class="app-container">
        <div>
            <el-button size="mini" @click="showAddAyDialog()">上传案由</el-button>
        </div>

        <el-table highlight-current-row border fit
            :data="ayList"
        >
            <!-- <el-table-column type="selection" width="55"></el-table-column> -->

            <el-table-column label="ID" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.id }}</span>
                </template>
            </el-table-column>

            <el-table-column label="案由标题" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.title }}</span>
                </template>
            </el-table-column>

            <el-table-column label="案由类别" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.type }}</span>
                </template>
            </el-table-column>

            <el-table-column label="是否已标注" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.sfbz }}</span>
                </template>
            </el-table-column>

            <el-table-column label="Actions" align="center">
                <template slot-scope="{row, $index}">
                    <el-button size="mini" type="primary" @click="showEditDialog(row, $index)">
                        编辑
                    </el-button>
                    <el-button size="mini" type="danger" @click="showDeleteDialog(row,$index)">
                        删除
                    </el-button>
                </template>
            </el-table-column>

        </el-table>

        <!-- 添加案由的dialog -->
        <el-dialog
            title="添加新案由"
            :visible.sync="addAyDialogFormVisible"
        >
            <el-form :model="newAy">
                <el-form-item label="案由类别">
                    <el-select v-model="newAy.type">
                        <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="案由标题">
                    <el-input v-model="newAy.title"></el-input>
                </el-form-item>

                <el-form-item label="案由内容">
                    <el-input type="textarea" v-model="newAy.content"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="addAyDialogFormVisible = false" type="danger">取消</el-button>
                <el-button @click="handleAddAy" type="primary">确定</el-button>
            </div>

        </el-dialog>

        <!-- 查看、编辑某个案由的dialog -->
        <el-dialog
            title="编辑"
            :visible.sync="ayEditFormVisible"
        >
            <el-form :model="ayItem">
                <el-form-item label="案由标题">
                    <el-input v-model="ayItem.title"></el-input>
                </el-form-item>
                <el-form-item label="案由类别">
                    <el-input v-model="ayItem.type"></el-input>
                </el-form-item>
                <el-form-item label="案由文本">
                    <el-input type="textarea" v-model="ayItem.content"></el-input>
                </el-form-item>
                <el-form-item label="是否标注">
                    <el-select v-model="ayItem.sfbz">
                        <el-option v-for="item in bzOptions" :key="item.key" :label="item.value" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标注结果">
                    <el-input type="textarea" v-model="ayItem.bzjg"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="ayEditFormVisible = false">取消</el-button>
                <el-button @click="handleEditItem" type="primary">确认</el-button>
            </div>
        </el-dialog>

        <!-- 删除某个案由的dialog -->
        <el-dialog
            title="删除"
            :visible.sync="ayDeleteConfirmVisible"
        >
            <span>确定要删除题为"{{ deleteAyItem.title }}"的案由吗？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="ayDeleteConfirmVisible = false">取消</el-button>
                <el-button @click="handleDeleteItem" type="primary">确认</el-button>
            </div>
        </el-dialog>
    </div>

</template>
<script>
import { getAyList } from '@/api/ay'
import { getAyInfo } from '@/api/ay'
import { addAy } from '@/api/ay'
import { updateAyItem } from "@/api/ay"
import { deleteAyItem } from "@/api/ay";

// export const bzOptions = [
//     {key: 'yes', value: '是'},
//     {key: 'no', value: '否'}
// ]

export default {
    

    data() {
        return {
            //案由对象列表，每项的属性分别是：{id,type,title,content,sfbz, bzjg}
            ayList: [],
            //案由类型列表
            ayInfoList: [],

            //添加案由dialog
            addAyDialogFormVisible: false,
            //查看、编辑案由dialog
            ayEditFormVisible: false,
            //删除案由dialog
            ayDeleteConfirmVisible: false,

            bzOptions: [
                {key: 'yes', value: '是'},
                {key: 'no', value: '否'}
            ],


            //上传的新案由数据
            newAy: {
                type: null,
                title: null,
                content: null
            },

            //编辑dialog展示的数据
            ayItem: {
                index: null,
                id: null,
                type: null,
                title: null,
                content: null,
                sfbz: null,
                bzjg: null
            },
            //删除某案由需要的数据
            deleteAyItem: {
                index: null,
                id: null,
                title: null
            }

        

        }
    },
    created() {
        this.getAyData()
        this.getAyInfoData()

    },
    methods: {
        getAyData() {
            getAyList().then(response => {
                const { page } = response
                this.ayList = page.list
                for(let i = 0 ; i < this.ayList.length ; ++i){
                    if(this.ayList[i].sfbz == 0){
                        this.ayList[i].sfbz = "否"
                    }else{
                        this.ayList[i].sfbz = "是"
                    }
                }
            })
        },
        getAyInfoData() {
            getAyInfo().then(response => {
                const { page } = response
                this.ayInfoList = page.list

            })
        },

        showAddAyDialog() {
            this.addAyDialogFormVisible = true
        },
        handleAddAy() {
            addAy({type: this.newAy.type, title: this.newAy.title, content: this.newAy.content}).then(response => {
                //添加新的案由后，在后台添加案由数据
                const { code,msg } = response
                if(code == 20000){
                    //直接更新当前页面的案由列表，不需要重新从服务器读取数据
                    this.ayList.push({
                        id: this.ayList.length + 1,
                        title: this.newAy.title,
                        type: this.newAy.type,
                        sfbz: '否',
                        content: this.newAy.content,
                        bzjg: null
                    }),

                    this.addAyDialogFormVisible = false

                    this.$notify({
                        title: 'Success',
                        message: '添加成功',
                        type: 'success',
                        duration: 2000
                    }),
                    //清空表单的新数据
                    newAy.type = null
                    newAy.title = null
                    newAy.content = null

                }else{
                    this.$notify({
                        title: 'Failure',
                        message: '添加失败,' + msg,
                        type: 'error',
                        duration: 2000
                    })
                }


            })
        },
        showEditDialog(row, index){
            this.ayItem.index = index
            this.ayItem.id = row.id
            this.ayItem.type = row.type
            this.ayItem.title = row.title
            this.ayItem.content = row.content
            this.ayItem.sfbz = row.sfbz
            this.ayItem.bzjg = row.bzjg
            this.ayEditFormVisible = true
        },
        showDeleteDialog(row, index){
            this.deleteAyItem.index = index
            this.deleteAyItem.id = row.id
            this.deleteAyItem.title = row.title
            this.ayDeleteConfirmVisible = true
        },
        handleEditItem(){
            //远程更新+本地前端数据更新
            updateAyItem({id: this.ayItem.id, type: this.ayItem.type, title: this.ayItem.title, content: this.ayItem.content, sfbz: this.ayItem.sfbz == '是' ? 1 : 0, bzjg: this.ayItem.bzjg}).then(response => {
                const { code,msg } = response
                if(code == 20000){
                    this.ayList[this.ayItem.index].type = this.ayItem.type
                    this.ayList[this.ayItem.index].title = this.ayItem.title
                    this.ayList[this.ayItem.index].content = this.ayItem.content
                    this.ayList[this.ayItem.index].sfbz = this.ayItem.sfbz
                    this.ayList[this.ayItem.index].bzjg = this.ayItem.bzjg

                    this.ayEditFormVisible = false
                    this.$notify({
                        title: 'Success',
                        message: '修改成功',
                        type: 'success',
                        duration: 2000
                    })
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
        handleDeleteItem(){
            //远程删除+本地前端数据删除
            deleteAyItem({id: this.deleteAyItem.id}).then(response => {
                const { code,msg } = response
                if(code == 20000) {
                    this.ayList.splice(this.deleteAyItem.index, 1)
                    this.ayDeleteConfirmVisible = false
                    this.$notify({
                        title: 'Success',
                        message: '删除成功',
                        type: 'success',
                        duration: 2000
                    })
                }else {
                    this.$notify({
                        title: 'Failure',
                        message: '删除失败,' + msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            })
        },
    }
}
</script>
<style scoped>

</style>