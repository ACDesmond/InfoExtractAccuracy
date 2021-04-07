<template>
    <div class="app-container">
        <div>
            <el-button size="mini" @click="addAyType()">添加新案由类别</el-button>
        </div>

        <el-table highlight-current-row border fit
            :data="ayInfoData"
        >
            <!-- <el-table-column type="selection" width="55"></el-table-column> -->

            <el-table-column label="ID" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.id }}</span>
                </template>
            </el-table-column>

            <el-table-column label="案由类别" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.type }}</span>
                </template>
            </el-table-column>

            <el-table-column label="拥有信息项个数" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.yyxxxgs }}</span>
                </template>
            </el-table-column>

            <el-table-column label="Actions" align="center">
                <template slot-scope="{row, $index}">
                    <el-button size="mini" type="primary" @click="showEditInfoForm(row, $index)">
                        编辑
                    </el-button>
                    <el-button size="mini" type="danger" @click="handleDeleteItem(row,$index)">
                        删除
                    </el-button>
                </template>
            </el-table-column>

        </el-table>

        <!-- 编辑案由信息的dialog -->
        <el-dialog
            title="编辑"
            :visible.sync="editAyInfoFormVisible"
        >
            <el-form :model="editAyItem">
                <el-form-item label="案由类别">
                    <el-input v-model="editAyItem.type"></el-input>
                </el-form-item>
                <el-form-item label="拥有信息项个数">
                    <el-input v-model="editAyItem.yyxxxgs"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editAyInfoFormVisible = false" type="danger">取消</el-button>
                <el-button @click="handleEditAyInfo" type="primary">确定</el-button>
            </div>
        </el-dialog>

        <!-- 添加案由信息的dialog -->
        <el-dialog
            title="添加案由项"
            :visible.sync="addAyDialogFormVisible"
            width="30%"
            
        >
            <el-form>
                <el-form-item label="请输入案由项名称">
                    <el-input v-model="newaymc" autocomplete="off" placeholder="添加多个案由项，请用','隔开"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addAyDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddAyInfo()">确 定</el-button>
            </div>
        </el-dialog>

    </div>

</template>
<script>
import { getAyInfo } from '@/api/ay'
import { addAyInfo } from '@/api/ay'
import { updateAyInfo } from "@/api/ay";

export default {
    data() {
        return {
            //案由信息数据,对象属性为{id, type, yyxxxgs}
            ayInfoData: [],

            addAyDialogFormVisible: false,
            //新添加的案由项名称
            newaymc: '',

            //编辑案由信息的dialog
            editAyInfoFormVisible: false,

            editAyItem: {
                index: null,
                id: null,
                type: null,
                yyxxxgs: null
            }

        }
    },
    created() {
        this.getData()
    },

    methods: {
        getData() {
            getAyInfo().then(response => {
                const { page } = response
                this.ayInfoData = page.list
            })
        },
        //删除单一案由项
        handleDeleteItem(row, index){
            this.$notify({
                title: 'Success',
                message: '删除成功',
                type: 'success',
                duration: 2000
            }),
            this.ayInfoData.splice(index, 1)
        },
        //批量删除案由项
        handleDelete(){
            //todo 暂时不要这个
        },
        //添加案由项
        addAyType() {
            this.addAyDialogFormVisible = true
        },
        handleAddAyInfo() {
            console.log('新建的案由是：' + this.newaymc)
            addAyInfo({newaymc: this.newaymc}).then(response => {
                
                //添加新案由项后，后台在数据库中添加
                const { code, msg } = response
                if(code == 20000){
                    this.ayInfoData.push({
                        id: this.ayInfoData.length + 1,
                        type: this.newaymc,
                        yyxxxgs: 0
                    })

                    this.addAyDialogFormVisible = false

                    this.$notify({
                        title: 'Success',
                        message: '添加成功',
                        type: 'success',
                        duration: 2000
                    })
                }else{
                    this.$notify({
                        title: 'Failure',
                        message: msg,
                        type: 'error',
                        duration: 2000
                    })
                }

                
            })
        },
        showEditInfoForm(row, index) {
            this.editAyItem.index = index
            this.editAyItem.id = row.id
            this.editAyItem.type = row.type
            this.editAyItem.yyxxxgs = row.yyxxxgs
            this.editAyInfoFormVisible = true
        },
        handleEditAyInfo() {
            updateAyInfo({id: this.editAyItem.id, type: this.editAyItem.type, yyxxxgs: this.editAyItem.yyxxxgs}).then(response => {
                //远程更新+本地前端数据更新
                const { code, msg } = response
                if(code == 20000){
                    this.ayInfoData[this.editAyItem.index].type = this.editAyItem.type
                    this.ayInfoData[this.editAyItem.index].yyxxxgs = this.editAyItem.yyxxxgs
                    this.editAyInfoFormVisible = false
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
        }
    }
}
</script>
<style scoped>

</style>