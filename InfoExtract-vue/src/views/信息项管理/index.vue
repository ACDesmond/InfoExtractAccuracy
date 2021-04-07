<template>
  <div class="app-container">
    <div style="margin-bottom: 20px">
      <el-button size="mini" @click="handleAdd()">添加信息项</el-button>
      <el-button size="mini" type="danger" @click="handleDeleteXxx()">删除信息项</el-button>
    </div>

    <el-table
      :data="tableData.filter(data => !search || data.xxxmc.toLowerCase().includes(search.toLowerCase()) || data.xxxsxjc.toLowerCase().includes(search.toLowerCase()) || data.sfyycqzcx.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column label="信息项名称" prop="xxxmc"></el-table-column>
      <el-table-column label="信息项缩写简称" prop="xxxsxjc"></el-table-column>
      <el-table-column label="所属案由个数" prop="ssaygs"></el-table-column>
      <el-table-column label="是否拥有抽取子程序" prop="sfyycqzcx"></el-table-column>
      <el-table-column align="right" width="660">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" style="width: 260px" />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" @click="handleAyInfo(scope.$index, scope.row)">查看所属案由</el-button>
          <el-tooltip content="添加抽取子程序或覆盖已有抽取子程序" placement="top">
            <el-button size="mini" @click="handleAddCqzcx(scope.$index, scope.row)">添加抽取子程序</el-button>
          </el-tooltip>
          <el-button size="mini" @click="handleViewCqzcx(scope.$index, scope.row)">查看抽取子程序</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="添加信息项"
      :visible.sync="addXxxDialogFormVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="addXxxForm">
        <el-form-item label="信息项名称" :label-width="formLabelWidth">
          <el-input v-model="addXxxForm.xxxmc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="信息项缩写简称" :label-width="formLabelWidth">
          <el-input v-model="addXxxForm.xxxsxjc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addXxxDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddXxx()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="编辑信息项"
      :visible.sync="editXxxDialogFormVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="editXxxForm">
        <el-form-item label="信息项名称" :label-width="formLabelWidth">
          <el-input v-model="editXxxForm.xxxmc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="信息项缩写简称" :label-width="formLabelWidth">
          <el-input v-model="editXxxForm.xxxsxjc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editXxxDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditXxx()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="信息项所属案由"
      :visible.sync="ayInfoDialogTableVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-table :data="ayInfoTableData" style="width: 100%">
        <el-table-column prop="aymc" label="案由名称"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ayInfoDialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="添加抽取子程序"
      :visible.sync="addCqzcxDialogFormVisible"
      width="30%"
      :before-close="handleCloseAddCqzcxDialogForm"
    >
      <el-upload
        class="upload-demo"
        ref="upload"
        action="http://localhost:8092/infoextract/xxx/addCqzcx/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false"
        :limit="1"
        :on-exceed="handleExceed"
        :headers="upload_headers"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button
          style="margin-left: 10px;"
          size="small"
          type="success"
          @click="submitUpload"
        >上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传java文件</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fileList = []; addCqzcxDialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="查看抽取子程序"
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
import axios from "axios";

export default {
  // 初始化获取数据
  created: function() {
    axios.get("http://localhost:8092/infoextract/xxx/list").then(response => {
      console.log(response.data.page.list);
      this.tableData = response.data.page.list;
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].sfyycqzcx == 0) {
          this.tableData[i].sfyycqzcx = "否";
        } else {
          this.tableData[i].sfyycqzcx = "是";
        }
      }
    });
  },

  data() {
    return {
      // table数据
      tableData: [],
      // table选择
      multipleSelection: [],
      // 搜索文本
      search: "",
      // 控制添加信息项表单是否显示
      addXxxDialogFormVisible: false,
      // 添加信息项表单数据
      addXxxForm: {
        xxxmc: "",
        xxxsxjc: ""
      },
      // 表单label宽度
      formLabelWidth: "120px",
      // 控制编辑信息项表单是否显示
      editXxxDialogFormVisible: false,
      // 编辑信息项表单数据
      editXxxForm: {
        id: "",
        xxxmc: "",
        xxxsxjc: ""
      },
      // 控制查看所属案由列表是否显示
      ayInfoDialogTableVisible: false,
      // 查看所属案由列表数据
      ayInfoTableData: [],

      // 控制添加抽取子程序表单是否显示
      addCqzcxDialogFormVisible: false,
      // 上传文件列表
      fileList: [],
      upload_headers: {
        xxx_id: "", // 信息项id
        sfyycqzcx: "" // 是否拥有抽取子程序
      },

      // 控制查看抽取子程序表单是否显示
      viewCqzcxDialogFormVisible: false,
      // 抽取子程序源代码
      cqzcx_ydm: ""
    };
  },
  methods: {
    // 查看抽取子程序
    handleViewCqzcx(index, row) {
      if (row.sfyycqzcx == "否") {
        this.$message({
          message: "该信息项没有抽取子程序",
          type: "warning"
        });
        return;
      } else {
        this.cqzcx_ydm = row.ydm;
        this.viewCqzcxDialogFormVisible = true;
      }
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    submitUpload() {
      this.$refs.upload.submit();

      // 重新获取数据
      axios.get("http://localhost:8092/infoextract/xxx/list").then(response => {
        this.tableData = response.data.page.list;
      });
    },

    handleAdd() {
      this.addXxxForm.xxxmc = "";
      this.addXxxForm.xxxsxjc = "";
      this.addXxxDialogFormVisible = true;
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.editXxxForm.id = row.id;
      this.editXxxForm.xxxmc = row.xxxmc;
      this.editXxxForm.xxxsxjc = row.xxxsxjc;
      this.editXxxDialogFormVisible = true;
    },

    // 添加抽取子程序（服务器交互）
    handleAddCqzcx(index, row) {
      this.addCqzcxDialogFormVisible = true;
      this.upload_headers.xxx_id = row.id;
      this.upload_headers.sfyycqzcx = row.sfyycqzcx == "是" ? 1 : 0;
      console.log(this.upload_headers);
    },
    // 查看所属案由（服务器交互）
    handleAyInfo(index, row) {
      console.log(index, row);

      axios
        .get("http://localhost:8092/infoextract/xxx/ayList?xxx_id=" + row.id)
        .then(response => {
          console.log(response);
          this.ayInfoTableData = response.data.list;
          this.ayInfoDialogTableVisible = true;
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    handleCloseAddCqzcxDialogForm(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          this.fileList = [];
          done();
        })
        .catch(_ => {});
    },
    // 添加信息项（服务器交互）
    handleAddXxx() {
      // 验证表单非空
      if (this.addXxxForm.xxxmc == "" || this.addXxxForm.xxxsxjc == "") {
        this.$message({
          message: "表单不能为空",
          type: "warning"
        });
        return;
      }

      // 上传信息项表单到服务器
      axios
        .post("http://localhost:8092/infoextract/xxx/save", this.addXxxForm)
        .then(response => {
          // 关闭dialog
          this.addXxxDialogFormVisible = false;
          // 信息提示
          this.$message({
            message: "添加信息项成功",
            type: "success"
          });
          // 重新获取数据
          axios
            .get("http://localhost:8092/infoextract/xxx/list")
            .then(response => {
              this.tableData = response.data.page.list;
            });
        })
        .catch(error => {
          // 关闭dialog
          this.addXxxDialogFormVisible = false;
          // 信息提示
          this.$message.error("添加信息项失败");
        });
    },
    // 编辑信息项（服务器交互）
    handleEditXxx() {
      // 验证表单非空
      if (this.editXxxForm.xxxmc == "" || this.editXxxForm.xxxsxjc == "") {
        this.$message({
          message: "表单不能为空",
          type: "warning"
        });
        return;
      }

      // 上传信息项表单到服务器
      axios
        .post("http://localhost:8092/infoextract/xxx/update", this.editXxxForm)
        .then(response => {
          // 关闭dialog
          this.editXxxDialogFormVisible = false;
          // 信息提示
          this.$message({
            message: "编辑信息项成功",
            type: "success"
          });
          // 重新获取数据
          axios
            .get("http://localhost:8092/infoextract/xxx/list")
            .then(response => {
              this.tableData = response.data.page.list;
            });
        })
        .catch(error => {
          // 关闭dialog
          this.addXxxDialogFormVisible = false;
          // 信息提示
          this.$message.error("编辑信息项失败");
        });
    },
    // 删除信息项（服务器交互）
    handleDeleteXxx() {
      this.$confirm(
        "此操作将永久删除" +
          this.multipleSelection.length +
          "条信息项, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          var deleteIds = [];
          for (let i = 0; i < this.multipleSelection.length; i++) {
            deleteIds.push(this.multipleSelection[i].id);
          }

          axios
            .post("http://localhost:8092/infoextract/xxx/delete", deleteIds)
            .then(response => {
              // 信息提示
              this.$message({
                message: "删除成功",
                type: "success"
              });
              // 重新获取数据
              axios
                .get("http://localhost:8092/infoextract/xxx/list")
                .then(response => {
                  this.tableData = response.data.page.list;
                });
            })
            .catch(error => {
              // 信息提示
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
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
    }
  },
  components: {
    editor: require("vue2-ace-editor")
  }
};
</script>

<style scoped>
</style>

