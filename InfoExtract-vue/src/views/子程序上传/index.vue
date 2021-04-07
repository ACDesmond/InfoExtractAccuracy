<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item>
        <el-select v-model="form.aytype" filterable placeholder="案由类型" @change="changeOptions">
          <el-option v-for="item in ayInfoList" :key="item.id" :label="item.type" :value="item.type"></el-option>
        </el-select>
        <el-select v-model="form.xxx" filterable placeholder="信息项">
          <el-option v-for="item in xxxList" :key="item.id" :label="item.xxxmc" :value="item.xxxmc"></el-option>
        </el-select>
      </el-form-item>
      
      <el-form-item label="自定义信息项">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="信息项缩写">
        <el-input v-model="form.sx" />
      </el-form-item>
      <el-button @click="test">test</el-button>
      
      <el-form-item style="text-align: center">
        <el-upload
          class="upload-demo"
          ref="upload"
          drag
          accept=".java"
          action="/infoextract/sh/addshzcx/"
          :data="form"
          :headers="uploadHeaders"
          :before-upload="onBeforeUpload"
          :before-remove="onBeforeRemove"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="successUpload"
          :file-list="fileList"
          :auto-upload="false"
          :limit="1"
          :on-exceed="handleExceed">
          <!-- :headers="upload_headers"
          > -->
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传java文件</div>
        </el-upload>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="success" @click="submitUpload">上传到服务器</el-button>
      </el-form-item>

    </el-form>
    <!-- <el-select
      v-model="value"
      filterable
      placeholder="请选择信息项"
      style="margin-bottom: 20px"
    >
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
    </el-select> -->

    

    <!-- <el-upload
      class="upload-demo"
      ref="upload"
      action="http://localhost:8092/infoextract/cqzcx/posts/"
      accept=".java"
      :before-upload="onBeforeUpload"
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
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">只能上传java文件</div>
    </el-upload> -->
  </div>
</template>

<script>
import { getXxxList } from '@/api/xxx'
import { getAyInfo } from '@/api/ay'

export default {
  //页面加载时获取信息项名称
  created() {
    this.getAyInfoData()
    getXxxList().then(response => {
      const { code, msg, page } = response
      this.options = page.list
    });
    this.uploadHeaders = { 'X-Token' : this.$store.getters.token}
  },
  data() {
    return {
      //案由列表
      ayInfoList: [],
      //当前select中的信息项列表
      xxxList: [],
      //信息项列表
      options: [],

      //提交uploader上传的数据
      form: {
        aytype: '',
        xxx: '',
        name: '',
        sx: '',
        uploader: this.$store.getters.name
      },
      fileList: [],
      uploadHeaders: {}
    };
  },
  methods: {
    getAyInfoData() {
      getAyInfo().then(response => {
        const { page } = response
        this.ayInfoList = page.list
      })
    },
    // onSelectChange(value) {
    //   console.log('选中的变化为' + value.id)
    //   this.form.xxx_id = value.id
    // },
    //修改案由option后，改变信息项option的方法
    changeOptions(selectVal) {
      for(let i = 0 ; i < this.options.length ; ++i){
        if(this.options[i].aytype == selectVal){
          this.xxxList.push({
            id: this.options[i].id,
            xxxmc: this.options[i].xxxmc
          })
        }
      }
    },
    test() {
      console.log(this.form.xxx)
      console.log(this.form.name)
      console.log(this.form.sx)
    },
    // 文件上传前
    onBeforeUpload(file) {
      let extension = file.name.substring(file.name.lastIndexOf('.')+1)
      const isJavaFile = extension === 'java'
      if(!isJavaFile){
        this.$message.error('上传的文件只能是Java文件')
      }
      return isJavaFile
    },
    submitUpload() {     
      // console.log(this.$refs.upload.file.name) 
      // if(this.$refs.upload.fileList.length == 0){
      //   this.$message.warning('您还没有选择上传的文件')
      //   return false
      // }
      this.$refs.upload.submit();
    },
    onBeforeRemove(file, fileList) {
      return this.$confirm('确定移除' +  file.name + '?')
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
    },
    successUpload(response, file, fileList){
      console.log("响应是：")
      console.log(response.code)
      console.log(response.msg)
      this.$message.info('上传成功')
    }
  }
};
</script>

<style scoped>
</style>

