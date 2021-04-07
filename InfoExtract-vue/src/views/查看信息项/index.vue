<template>
  <div class="app-container">


    <el-table highlight-current-row border fit 
      :data="tableData.filter(data => !search || data.xxxmc.toLowerCase().includes(search.toLowerCase()) || data.xxxsxjc.toLowerCase().includes(search.toLowerCase()) || data.sfyycqzcx.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
    >
      <el-table-column label="ID" align="center">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

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

      <el-table-column label="子程序上传者" align="center">
        <template slot-scope="{row}">
          <span>{{ row.zcxscz }}</span>
        </template>
      </el-table-column>

      <el-table-column align="right">
        <!-- <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" style="width: 260px" />
        </template> -->
        <template slot-scope="scope">
          <el-button size="mini" @click="handleViewCqzcx(scope.$index, scope.row)">查看抽取子程序</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="子程序源代码"
      :visible.sync="viewCqzcxDialogFormVisible"
      width="50%"
      :before-close="handleClose"
    >
      <editor
        v-model="cqzcx_ydm"
        @init="editorInit"
        lang="java"
        theme="chrome"
        height="400"
        style="font-size: 16px"
      ></editor>

      <div slot="footer" class="dialog-footer">
        <el-button @click="viewCqzcxDialogFormVisible = false">确 定</el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>
import axios from 'axios'
import { getXxxList } from '@/api/xxx'
export default {
    data() {
        return {
          //表格数据，信息项数据
          tableData: [],

          //搜索文本
          search: "",

          //显示抽取子程序
          viewCqzcxDialogFormVisible: false,
          cqzcx_ydm: ""
        }
    },
    created() {
      this.getList()
    },

    
    methods: {
      getList() {
        getXxxList().then(response => {
          const { code, msg, page } = response
          this.tableData = page.list
          for (let i = 0;  i < this.tableData.length; ++i){
            if (this.tableData[i].sfyycqzcx == 0) {
              this.tableData[i].sfyycqzcx = "否";
            } else {
              this.tableData[i].sfyycqzcx = "是";
            }
          }
        });
      },
      //查看抽取子程序
      handleViewCqzcx(index, row) {
        console.log("handleviewcqzcx: " + index + row)
        if(row.sfyycqzcx == "否") {
          this.$message({
            message: "该信息项没有抽取子程序",
            type: "warning"
          });
          return;
        } else {
          this.cqzcx_ydm = row.ydm
          this.viewCqzcxDialogFormVisible = true;
        }
      },
      handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
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