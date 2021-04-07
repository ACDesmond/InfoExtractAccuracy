<template>
  <div class="app-container">
    <el-table highlight-current-row border fit
    :data="shData"
    @selection-change="handleSelectionChange"
    >
      <el-table-column type="select" width="55">
      </el-table-column>

      <el-table-column label="ID" align="center">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="信息项名称" align="center">
        <template slot-scope="{row}">
          <span v-if="row.newxxx === '是'">{{ row.xxxname }}</span>
          <span v-else>{{ row.xxxmc }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="是否新建信息项" align="center">
        <template slot-scope="{row}">
          <span>{{ row.newxxx }}</span>
        </template>
      </el-table-column> -->

      <!-- <el-table-column label="新建信息项名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.xxxname }}</span>
        </template>
      </el-table-column> -->

      <!-- <el-table-column label="新建信息项缩写" align="center">
        <template slot-scope="{row}">
          <span>{{ row.xxxsx }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="上传者" align="center">
        <template slot-scope="{row}">
          <span>{{ row.uploader }}</span>
        </template>
      </el-table-column>

      <el-table-column label="审核状态" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            <span v-if="row.status === 0">待审核</span> 
            <span v-else-if="row.status === 1">通过</span>
            <span v-else>不通过</span>
          </el-tag>
        </template>
      </el-table-column>

      <!-- <el-table-column label="代码文件名" align="center">
        <template slot-scope="{row}">
          <span>{{ row.wjm }}</span>
        </template>
      </el-table-column> -->

      <!-- 查看详细 -->
      <el-table-column label="查看" align="center">
        <template slot-scope="scope">
          <el-button @click="handleViewDetail(scope.$index, scope.row)">点击查看详细</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- <el-button type="success" @click="submitcheck">提交审核</el-button> -->

    <!-- 子程序详细审核dialog -->
    <el-dialog
      title="信息项子程序"
      :visible.sync="viewCqzcxDialogFormVisible"
      width="50%"
    >
      <el-form ref="form" :model="form">
        <el-form-item label="所属案由类型">
          <span>{{ form.aytype }}</span>
        </el-form-item>
        <el-form-item label="信息项名称">
          <span>{{ form.xxxmc }}</span>
        </el-form-item>
        <el-form-item label="是否新建信息项">
          <span v-if="form.newxxx === '是'">{{ form.xxxname }}</span>
          <span v-else>{{ form.xxxmc }}</span>
        </el-form-item>
        <el-form-item label="信息项缩写简称" v-if="form.newxxx === '是'">
          <span>{{ form.xxxsx }}</span>
        </el-form-item>
        <el-form-item label="上传者">
          <span>{{ form.uploader }}</span>
        </el-form-item>
        <el-form-item label="文件名">
          <span>{{ form.wjm }}</span>
        </el-form-item>
        <el-form-item label="源代码">
          <editor
            v-model="form.ydm"
            @init="editorInit"
            lang="java"
            theme="chrome"
            height="400"
            style="font-size: 16px"
          ></editor>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.comment" type="textarea" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="checkpass" type="success" round>审核通过</el-button>
        <el-button @click="checknotpass" type="danger" round>审核不通过</el-button>
      </div>


    </el-dialog>

    <el-dialog
      title="提交审核"
      :visible.sync="viewSubmitCheckVisible"
      width="50%"
    >
      <template>
        <span>您已审核完成，确认提交结果？</span>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewSubmitCheckVisible = false">取消</el-button>
        <el-button @click="confirmSubmit">确认</el-button>
      </div>
    </el-dialog>

    <el-button @click="test()">test</el-button>
    

  </div>
</template>

<script>
import { getShXxxList } from "@/api/xxx";
import { submitCheck } from "@/api/xxx";

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        0: 'info',
        2: 'danger'
      }
      return statusMap[status]
    }
  },

  data() {
    return {
      //审核数据
      shData: [],

      //显示抽取子程序
      viewCqzcxDialogFormVisible: false,

      //提交审核
      viewSubmitCheckVisible: false,

      // 当前审核的数据行索引
      cur_row_index: "",

      //dialog表单数据
      form: {
        id: "",
        aytype: "",
        xxxmc: "",
        newxxx: "",
        xxxname: "",
        xxxsx: "",
        uploader: "",
        wjm: "",
        ydm: "",
        status: "",
        comment: ""
      }
    };
  },

  created() {
    this.getList()
  },
  methods: {
    test() {
      this.$notify({
            title: "Success",
            message: "审核提交成功",
            type: "success",
            duration: 2000
          })
    },
    getList() {
      // item data format of shData is [id, newxxx, xxxmc, xxxname, xxxsx, uploader, wjm, ydm, status, comment]
      getShXxxList().then(response => {
        const { page } = response
        this.shData = page.list
        for(let i = 0 ; i < this.shData.length ; ++i) {
          console.log("zhuangtai:  "+this.shData[i].state)
          if(this.shData[i].newxxx == 0) {
            this.shData[i].newxxx = "否"
          } else {
            this.shData[i].newxxx = "是"
          }
        }
      })
    },

    handleViewDetail(index, row) {
      this.cur_row_index = index
      this.form.id = row.id
      this.form.aytype = row.aytype
      this.form.newxxx = row.newxxx
      this.form.xxxmc = row.xxxmc
      this.form.xxxname = row.xxxname
      this.form.xxxsx = row.xxxsx
      this.form.uploader = row.uploader
      this.form.wjm = row.wjm
      this.form.ydm = row.ydm
      this.form.comment = row.comment
      this.viewCqzcxDialogFormVisible = true
    },

    // 当前信息项子程序审核通过
    // id, check result, comment
    checkpass() {
      //先修改前端审核状态
      let cur_row = this.shData[this.cur_row_index]
      cur_row.status = 1
      submitCheck({id: cur_row.id, status: cur_row.status, comment: this.form.comment}).then(response => {
        const { code } = response
        if(code = 20000){
          console.log("审核成功,通过")
          this.$notify({
            title: "Success",
            message: "审核提交成功",
            type: "success",
            duration: 2000
          })
        }
      })
      // //准备往后段传数据
      // console.log("审核表id: "+cur_row.id)
      // console.log("当前审核状态: "+cur_row.status)
      // console.log("备注："+this.form.comment)
      
      this.viewCqzcxDialogFormVisible = false
    },
    // 当前信息项子程序审核不通过
    checknotpass() {
      let cur_row = this.shData[this.cur_row_index]
      cur_row.status = 2
      submitCheck({id: cur_row.id, status: cur_row.status, comment: this.form.comment}).then(response => {
        const { code } = response
        if(code = 20000){
          console.log("审核成功，不通过")
          this.$notify({
            title: "Success",
            message: "审核提交成功",
            type: "success",
            duration: 2000
          })
        }
      })
      // console.log(cur_row.id)
      // console.log(cur_row.status)
      // console.log(this.form.comment)
      this.viewCqzcxDialogFormVisible = false
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
    //点击提交审核button
    submitcheck() {
      this.viewSubmitCheckVisible = true
    },
    //确认提交审核
    confirmSubmit() {

    },
    handleSelectionChange() {

    }
  },

  components: {
    editor: require("vue2-ace-editor")
  }
};
</script>

<style scoped>
</style>

