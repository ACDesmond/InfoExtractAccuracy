<template>
  <div class="app-container">
    <div style="margin-bottom: 20px">
      <el-button size="mini" @click="handleAdd()">添加案由</el-button>
      <el-button size="mini" type="danger" @click="handleDeleteAy()">批量删除案由</el-button>
    </div>

    <el-table
      :data="tableData.filter(data => !search || data.aymc.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="案由名称" prop="aymc"></el-table-column>
      <el-table-column label="拥有信息项个数" prop="yyxxxgs"></el-table-column>
      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" @click="handleXxxInfo(scope.$index, scope.row)">查看拥有信息项</el-button>
          <el-button size="mini" @click="handleXxxEdit(scope.$index, scope.row)">编辑拥有信息项</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="添加案由"
      :visible.sync="addAyDialogFormVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="addAyForm">
        <el-form-item label="案由名称" :label-width="formLabelWidth">
          <el-input v-model="addAyForm.aymc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addAyDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddAy()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="编辑案由"
      :visible.sync="editAyDialogFormVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="editAyForm">
        <el-form-item label="案由名称" :label-width="formLabelWidth">
          <el-input v-model="editAyForm.aymc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editAyDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditAy()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="查看拥有信息项"
      :visible.sync="xxxInfoDialogTableVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-table :data="xxxInfoTableData" style="width: 100%">
        <el-table-column prop="xxxmc" label="信息项名称"></el-table-column>
        <el-table-column prop="xxxsxjc" label="信息项缩写简称"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="xxxInfoDialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="编辑拥有信息项"
      :visible.sync="xxxEditDialogTableVisible"
      width="60%"
      :before-close="handleClose"
    >
      <DndList :list1="AllXxxData" :list2="xxxEditTableData"></DndList>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleXxxEditSubmit()">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

import DndList from "./DndList";

export default {
  // 初始化获取数据
  created: function() {
    axios.get("http://localhost:8092/infoextract/ay/list").then(response => {
      console.log(response.data.page.list);
      this.tableData = response.data.page.list;
    });

    axios.get("http://localhost:8092/infoextract/xxx/list").then(response => {
      console.log(response.data.page.list);
      this.AllXxxDataBackup = response.data.page.list;
    });
  },
  components: { DndList },
  data() {
    return {
      tableData: [],
      search: "",
      // table选择
      multipleSelection: [],

      // 表单label宽度
      formLabelWidth: "120px",

      // 控制添加案由表单是否显示
      addAyDialogFormVisible: false,
      addAyForm: {
        aymc: ""
      },

      // 控制编辑案由表单是否显示
      editAyDialogFormVisible: false,
      // 编辑案由表单数据
      editAyForm: {
        id: "",
        aymc: ""
      },

      // 查案拥有信息项数据
      xxxInfoTableData: [],
      // 控制查看拥有信息项对话框是否显示
      xxxInfoDialogTableVisible: false,

      // 编辑拥有信息项数据
      xxxEditTableData: [],
      // 控制编辑拥有信息项对话框是否显示
      xxxEditDialogTableVisible: false,
      // 全部信息项数据
      AllXxxData: [],
      // 全部信息项数据备份
      AllXxxDataBackup: [],
      selected_id: "",
    };
  },
  methods: {
    handleAdd() {
      this.addAyForm.aymc = "";
      this.addAyDialogFormVisible = true;
    },
    // 添加案由（服务器交互）
    handleAddAy() {
      // 验证表单非空
      if (this.addAyForm.aymc == "") {
        this.$message({
          message: "表单不能为空",
          type: "warning"
        });
        return;
      }
      // 上传信息项表单到服务器
      axios
        .post("http://localhost:8092/infoextract/ay/save", this.addAyForm)
        .then(response => {
          // 关闭dialog
          this.addAyDialogFormVisible = false;
          // 信息提示
          this.$message({
            message: "添加案由成功",
            type: "success"
          });
          // 重新获取数据
          axios
            .get("http://localhost:8092/infoextract/ay/list")
            .then(response => {
              this.tableData = response.data.page.list;
            });
        })
        .catch(error => {
          // 关闭dialog
          this.addAyDialogFormVisible = false;
          // 信息提示
          this.$message.error("添加案由失败");
        });
    },
    // 删除案由（服务器交互）
    handleDeleteAy() {
      this.$confirm(
        "此操作将永久删除" +
          this.multipleSelection.length +
          "条案由, 是否继续?",
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
            .post("http://localhost:8092/infoextract/ay/delete", deleteIds)
            .then(response => {
              // 信息提示
              this.$message({
                message: "删除成功",
                type: "success"
              });
              // 重新获取数据
              axios
                .get("http://localhost:8092/infoextract/ay/list")
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
    handleEdit(index, row) {
      console.log(index, row);
      this.editAyForm.id = row.id;
      this.editAyForm.aymc = row.aymc;
      this.editAyDialogFormVisible = true;
    },
    // 编辑案由（服务器交互）
    handleEditAy() {
      // 验证表单非空
      if (this.editAyForm.aymc == "") {
        this.$message({
          message: "表单不能为空",
          type: "warning"
        });
        return;
      }

      // 上传案由表单到服务器
      axios
        .post("http://localhost:8092/infoextract/ay/update", this.editAyForm)
        .then(response => {
          // 关闭dialog
          this.editAyDialogFormVisible = false;
          // 信息提示
          this.$message({
            message: "编辑案由成功",
            type: "success"
          });
          // 重新获取数据
          axios
            .get("http://localhost:8092/infoextract/ay/list")
            .then(response => {
              this.tableData = response.data.page.list;
            });
        })
        .catch(error => {
          // 关闭dialog
          this.addAyDialogFormVisible = false;
          // 信息提示
          this.$message.error("编辑案由失败");
        });
    },

    // 查看拥有信息项（服务器交互）
    handleXxxInfo(index, row) {
      console.log(index, row);

      axios
        .get("http://localhost:8092/infoextract/ay/xxxList?ay_id=" + row.id)
        .then(response => {
          console.log(response);
          this.xxxInfoTableData = response.data.list;
          this.xxxInfoDialogTableVisible = true;
        });
    },

    // 编辑拥有信息项，获取案由的信息项（服务器交互）
    handleXxxEdit(index, row) {
      this.AllXxxData = [];
      this.xxxEditTableData = [];

      this.selected_id = row.id;

      console.log(index, row);
      axios
        .get("http://localhost:8092/infoextract/ay/xxxList?ay_id=" + row.id)
        .then(response => {
          console.log(response);
          this.xxxEditTableData = response.data.list;
          for (let i = 0; i < this.AllXxxDataBackup.length; i++) {
            let flag = false;
            for (let j = 0; j < this.xxxEditTableData.length; j++) {
              if (this.AllXxxDataBackup[i].id == this.xxxEditTableData[j].id) {
                flag = true;
                break;
              }
            }
            if (flag == false) {
              this.AllXxxData.push(this.AllXxxDataBackup[i]);
            }
          }
          this.xxxEditDialogTableVisible = true;
        });
    },
    // 编辑拥有信息项，提交案由的信息项（服务器交互）
    handleXxxEditSubmit() {
      axios
        .post(
          "http://localhost:8092/infoextract/ay/xxxList?id=" + this.selected_id,
          this.xxxEditTableData
        )
        .then(response => {
          this.xxxEditDialogTableVisible = false;
          this.$message({
            message: "编辑案由拥有信息项成功",
            type: "success"
          });
        })
        .catch(error => {
          // 关闭dialog
          this.xxxEditDialogTableVisible = false;
          // 信息提示
          this.$message.error("编辑案由拥有信息项失败");
        });
      this.xxxEditDialogTableVisible = false;
    },

    handleDelete(index, row) {
      console.log(index, row);
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }
  }
};
</script>

<style scoped>
</style>

