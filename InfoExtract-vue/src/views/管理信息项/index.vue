<template>
  <div class="app-container">
    <div style="margin-bottom: 20px">
      <el-button size="mini" icon="el-icon-edit" type="primary" @click="showAddXxxDialog">添加信息项</el-button>
      <!-- <el-button size="mini" type="danger" @click="handleDeleteXxx()">删除信息项</el-button> -->
    </div>

    <el-table highlight-current-row border fit
    :data="xxxList"
    >
        <el-table-column label="信息项名称" align="center">
            <template slot-scope="{row}">
                <span>{{ row.xxxmc }}</span>
            </template>
        </el-table-column>
        <el-table-column label="信息项缩写简称" align="center">
            <template slot-scope="{row}">
                <span>{{ row.xxxsxjc }}</span>
            </template>
        </el-table-column>
        <el-table-column label="所属案由类型" align="center">
            <template slot-scope="{row}">
                <span>{{ row.aytype }}</span>
            </template>
        </el-table-column>
        <el-table-column label="是否拥有抽取子程序" align="center">
            <template slot-scope="{row}">
                <span>{{ row.sfyycqzcx }}</span>
            </template>
        </el-table-column>
        <el-table-column label="Actions" align="center">
            <template slot-scope="{row, $index}">
                <el-button size="mini" type="info" @click="showDetailDialog(row,$index)">
                    查看
                </el-button>
                <el-button size="mini" type="primary" @click="showEditDialog(row, $index)">
                    编辑
                </el-button>
                <el-button size="mini" type="danger" @click="showDeleteDialog(row,$index)">
                    删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog
        title="添加信息项"
        :visible.sync="xxxAddFormVisible"
    >
        <el-form :model="newXxx">
            <el-form-item label="所属案由类型">
                <el-select v-model="newXxx.aytype">
                    <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="信息项名称">
                <el-input v-model="newXxx.xxxmc"></el-input>
            </el-form-item>
            <el-form-item label="信息项缩写简称">
                <el-input v-model="newXxx.xxxsxjc"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="xxxAddFormVisible = false">取消</el-button>
            <el-button @click="handleAdd" type="primary">确认</el-button>
        </div>

    </el-dialog>

    <!-- 查看信息项的dialog -->
    <el-dialog
        title="查看"
        :visible.sync="xxxDetailFormVisible"
    >
        <el-form :model="xxxItem">
            <el-form-item label="信息项名称">
                <span>{{ xxxItem.xxxmc }}</span>
            </el-form-item>
            <el-form-item label="信息项缩写简称">
                <span>{{ xxxItem.xxxsxjc }}</span>
            </el-form-item>
            <el-form-item label="是否拥有抽取子程序">
                <span>{{ xxxItem.sfyycqzcx }}</span>
            </el-form-item>
            <el-form-item label="案由所属类型">
                <span>{{ xxxItem.aytype }}</span>
            </el-form-item>
            <el-form-item label="子程序上传者">
                <span>{{ xxxItem.zcxscz }}</span>
            </el-form-item>
            <el-form-item label="文件名">
                <span>{{ xxxItem.wjm }}</span>
            </el-form-item>
            <el-form-item label="源代码">
                <editor
                    v-model="xxxItem.ydm"
                    @init="editorInit"
                    lang="java"
                    theme="chrome"
                    height="400"
                    style="font-size: 16px"
                ></editor>            
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="xxxDetailFormVisible = false" type="primary">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 编辑信息项的dialog -->
    <el-dialog
        title="编辑"
        :visible.sync="xxxEditFormVisible"

    >
        <el-form :model="xxxItem">
            <el-form-item label="信息项名称">
                <el-input v-model="xxxItem.xxxmc"></el-input>
            </el-form-item>
            <el-form-item label="信息项缩写简称">
                <el-input v-model="xxxItem.xxxsxjc"></el-input>
            </el-form-item>
            <el-form-item label="是否拥有抽取子程序">
                <el-select v-model="xxxItem.sfyycqzcx">
                    <el-option v-for="item in zcxOptions" :key="item.key" :label="item.value" :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="案由所属类型">
                <el-select v-model="xxxItem.aytype">
                    <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="子程序上传者">
                <el-input v-model="xxxItem.zcxscz"></el-input>
            </el-form-item>
            <el-form-item label="文件名">
                <el-input v-model="xxxItem.wjm"></el-input>
            </el-form-item>
            <el-form-item label="源代码">
                <editor
                    v-model="xxxItem.ydm"
                    @init="editorInit"
                    lang="java"
                    theme="chrome"
                    height="400"
                    style="font-size: 16px"
                ></editor>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="xxxEditFormVisible = false" type="error">取 消</el-button>
            <el-button @click="handleEdit" type="primary">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 删除某个信息项的dialog -->
    <el-dialog
        title="删除"
        :visible.sync="xxxDeleteFormVisible"

    >   
        <span>确定要删除“{{ deleteXxxItem.xxxmc }}”信息项吗？</span>
        <div slot="footer" class="dialog-footer">
            <el-button @click="xxxDeleteFormVisible = false">取消</el-button>
            <el-button @click="handleDelete" type="primary">确认</el-button>
        </div>
    </el-dialog>

  </div>
</template>

<script>
import { getXxxList } from '@/api/xxx'
import { getAyInfo } from '@/api/ay'
import { addXxx } from '@/api/xxx'
import { editXxx } from '@/api/xxx'
import { deleteXxx } from '@/api/xxx'

// const zcxOptions = [
//     {key: 'yes', value: '是'},
//     {key: 'no', value: '否'}
// ]

export default {
    data() {
        return {
            xxxList: [],
            xxxAddFormVisible: false,
            xxxDetailFormVisible: false,
            xxxEditFormVisible: false,
            xxxDeleteFormVisible: false,

            ayInfoList: [],

            zcxOptions: [
                {key: 'yes', value: '是'},
                {key: 'no', value: '否'}
            ],

            //添加信息项
            newXxx: {
                xxxmc: null,
                xxxsxjc: null,
                aytype: null
            },

            //查看、编辑信息项
            xxxItem: {
                id: null,
                index: null,
                xxxmc: null,
                xxxsxjc: null,
                sfyycqzcx: null,
                aytype: null,
                zcxscz: null,
                wjm: null,
                ydm: null,
                zjm: null
            },

            //删除某条信息项
            deleteXxxItem: {
                xxxmc: null,
                index: null,
                id: null
            }
        }
    },
     created() {
         this.getXxxData()
         this.getAyType()
     },
     methods: {
         getXxxData() {
             getXxxList().then(response => {
                 const { page } = response
                 this.xxxList = page.list
                 for(let i = 0 ; i < this.xxxList.length ; ++i){
                     if(this.xxxList[i].sfyycqzcx == 1){
                         this.xxxList[i].sfyycqzcx = "是"
                     }else{
                         this.xxxList[i].sfyycqzcx = "否"
                     }
                 }
             })
         },
         getAyType() {
             getAyInfo().then(response => {
                 const { page } = response
                 this.ayInfoList = page.list
             })
         },
         showAddXxxDialog() {
             this.xxxAddFormVisible = true
         },
         showDetailDialog(row, index){
             this.xxxItem.xxxmc = row.xxxmc
             this.xxxItem.xxxsxjc = row.xxxsxjc
             this.xxxItem.sfyycqzcx = row.sfyycqzcx
             this.xxxItem.aytype = row.aytype
             this.xxxItem.zcxscz = row.zcxscz
             this.xxxItem.wjm = row.wjm
             this.xxxItem.ydm = row.ydm
             this.xxxDetailFormVisible = true
         },
         showEditDialog(row, index) {
             this.xxxItem.id = row.id
             this.xxxItem.index = index
             this.xxxItem.xxxmc = row.xxxmc
             this.xxxItem.xxxsxjc = row.xxxsxjc
             this.xxxItem.sfyycqzcx = row.sfyycqzcx
             this.xxxItem.aytype = row.aytype
             this.xxxItem.zcxscz = row.zcxscz
             this.xxxItem.wjm = row.wjm
             this.xxxItem.ydm = row.ydm
             this.xxxItem.zjm = row.zjm
             this.xxxEditFormVisible = true
         },
         showDeleteDialog(row, index) {
             this.deleteXxxItem.xxxmc = row.xxxmc
             this.deleteXxxItem.id = row.id
             this.deleteXxxItem.index = index
             this.xxxDeleteFormVisible = true
         },
         handleAdd() {
             addXxx({xxxmc: this.newXxx.xxxmc, xxxsxjc: this.newXxx.xxxsxjc, aytype: this.newXxx.aytype}).then(response => {
                 //添加新的信息项后，在后台添加信息项数据
                 const { code, msg } = response
                 if(code == 20000){
                     this.xxxList.push({
                         id: this.xxxList.length + 1,
                         xxxmc: this.newXxx.xxxmc,
                         xxxsxjc: this.newXxx.xxxsxjc,
                         ssaygs: 1,
                         sfyycqzcx: '否',
                         aytype: this.newXxx.aytype,
                         zcxscz: null,
                         ydm: null,
                         zjm: null,
                         wjm: null
                     }),

                     this.xxxAddFormVisible = false

                     this.$notify({
                        title: 'Success',
                        message: '添加成功',
                        type: 'success',
                        duration: 2000
                    }),
                    newXxx.xxxmc = null
                    newXxx.xxxsxjc = null
                    newXxx.aytype = null
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

         handleEdit() {
             editXxx({id: this.xxxItem.id, xxxmc: this.xxxItem.xxxmc, xxxsxjc: this.xxxItem.xxxsxjc,
                sfyycqzcx: this.xxxItem.sfyycqzcx, aytype: this.xxxItem.aytype, zcxscz: this.Item.zcxscz,
                wjm: this.xxxItem.wjm, ydm: this.xxxItem.ydm, zjm: this.xxxItem.zjm
             }).then(response => {
                 const { code, msg} = response
                 if(code == 20000){
                     this.xxxList[this.xxxItem.index].xxxmc = this.xxxItem.xxxmc
                     this.xxxList[this.xxxItem.index].xxxsxjc = this.xxxItem.xxxsxjc
                     this.xxxList[this.xxxItem.index].sfyycqzcx = this.xxxItem.sfyycqzcx
                     this.xxxList[this.xxxItem.index].aytype = this.xxxItem.aytype
                     this.xxxList[this.xxxItem.index].zcxscz = this.xxxItem.zcxscz
                     this.xxxList[this.xxxItem.index].wjm = this.xxxItem.wjm
                     this.xxxList[this.xxxItem.index].ydm = this.xxxItem.ydm

                     this.xxxEditFormVisible = false
                     this.$notify({
                        title: 'Success',
                        message: '编辑成功',
                        type: 'success',
                        duration: 2000
                    })

                 }else{
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
             deleteXxx({id: this.deleteXxxItem.id}).then(response => {
                 const { code, msg } = response
                 if(code == 20000){
                     this.xxxList.splice(this.deleteXxxItem.index, 1)
                     this.xxxDeleteFormVisible = false
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

         // 编辑器设定
        editorInit: function(editor) {
        require("brace/ext/language_tools"); //language extension prerequsite...
        require("brace/mode/java"); //language
        require("brace/theme/chrome");
        require("brace/snippets/java"); //snippet
        editor.setReadOnly(true);

        document.getElementsByClassName("ace_print-margin")[0].style.visibility =
            "hidden";
        },
     },
     components: {
         editor: require("vue2-ace-editor")
     }

}
</script>

<style>

</style>