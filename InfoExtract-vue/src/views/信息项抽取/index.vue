<template>
  <div class="app-container">
    <el-select
      v-model="upload_headers.aydm"
      filterable
      placeholder="请选择案由"
      style="margin-bottom: 20px"
    >
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
    </el-select>

    <el-upload
      class="upload-demo"
      ref="upload"
      action="http://localhost:8092/xxxcq/posts/"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :file-list="fileList"
      :auto-upload="false"
      :limit="1"
      :on-exceed="handleExceed"
      :headers="upload_headers"
      style="width: 400px"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button
        style="margin-left: 10px;"
        size="small"
        type="success"
        @click="submitUpload"
      >上传到服务器抽取信息</el-button>
      <div slot="tip" class="el-upload__tip">只能上传doc或docx文件</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  data() {
    return {
      options: [
        {
          value: "1",
          label: "房屋买卖合同纠纷"
        },
        {
          value: "2",
          label: "变更抚养关系纠纷"
        }
      ],
      upload_headers: {
        aydm: "" // 案由代码
      },
      fileList: []
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    }
  }
};
</script>

<style scoped>
</style>

