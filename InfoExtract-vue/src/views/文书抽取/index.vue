<template>
  <div class="app-container">

    <el-form label-width="120px">
        <el-form-item>
          <el-transfer 
            filterable
            :titles="['可选信息项','已选信息项']"
            :button-texts="['撤选','选择']"
            v-model="uploadData.selectedXxx" 
            :data="xxxData">
          </el-transfer>
        </el-form-item>

        <el-form-item style="text-align: center">
          <el-upload
            ref="upload"
            drag
            action="/infoextract/xxx/extract/"
            :data="user"
            :headers="uploadHeaders"
            :limit="1"
            :auto-upload="false"
            :on-exceed="handleExceed"
            :on-success="successUpload"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传.doc或.docx文件</div>

          </el-upload>
        </el-form-item>
        <el-form-item style="text-align: center">
            <el-button type="success" @click="startExtract">开始抽取</el-button>
        </el-form-item>

        
    </el-form>
    <el-button @click="testdownload">testdownload</el-button>
    <el-dialog
      title=""
      :visible.sync="confirmExtractVisible"
    >
        <el-form>
            <el-form-item label="您已选择信息项:"> 
              <span v-for="(item,i) in uploadData.selectedXxx" :key="i">{{ item }}  </span>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="confirmExtract">确认</el-button>
        </div>
    </el-dialog>
    <!-- <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;"> -->
    <!-- <div style="text-align: center">

    </div>
    <div style="text-align: center">

    </div> -->

  </div>
</template>

<script>
import { getXxxList } from "@/api/xxx"
import { extract } from '@/api/xxx'
import axios from 'axios'
import Qs from 'qs'

export default {
    data() {
      return {
        xxxData: [],
        //当前用户
        user: {
          user: this.$store.getters.name,
        },
        //上传到服务器的数据
        uploadData: {
          //当前用户
          user: this.$store.getters.name,
          //选中的要抽取的信息项名称
          selectedXxx: []
        },
        //upload的请求头
        uploadHeaders: {},
        //确认抽取dialog
        confirmExtractVisible: false
      }
    },

    created() {
        this.getList()
        this.uploadHeaders = { 'X-Token' : this.$store.getters.token}

    },

    methods: {
        getList() {
            getXxxList().then(response => {
                const { page } = response
                let xxx = page.list
                for(let i = 0 ; i < xxx.length ; ++i){
                    this.xxxData.push({
                        key: xxx[i].xxxmc,
                        label: xxx[i].xxxmc
                    });
                }
            })
        },
        startExtract() {
            this.confirmExtractVisible = true
        },
        confirmExtract() {
          this.confirmExtractVisible = false
          this.$refs.upload.submit()
        },
        testtransfer() {
          for(let i = 0 ; i < this.uploadData.selectedXxx.length ; ++i){
            console.log(this.uploadData.selectedXxx[i])
          }
        },
        testdownload() {
          // window.location.href = "http://localhost:8092/infoextract/xxx/extracttest"
          const url = "/infoextract/xxx/extracttest"
          const fileName = 'res.xml'
          const xxx = []
          for(let i = 0 ; i < this.uploadData.selectedXxx.length ; ++i){
            xxx.push(this.uploadData.selectedXxx[i])
          }
          // xxx.push('as')
          // xxx.push('er')
          Qs.stringify({xxx: xxx},{arrayFormat: 'repeat'})
          axios({
            method: 'post',
            data: {
              user: this.$store.getters.name,
              Xxx: xxx
            },
            url: url,
            headers: {
              'Content-Type': 'application/json',
              'X-Token': this.$store.getters.token
            },
            responseType: 'blob'
          }).then(response => {
            const blob = new Blob([response.data])
            if ('download' in document.createElement('a')){
              const link = document.createElement('a')//创建a标签
              link.download = fileName//a标签添加属性
              link.style.display = 'none'
              link.href = URL.createObjectURL(blob)
              document.body.appendChild(link)
              link.click()//执行下载
              URL.revokeObjectURL(link.href) //释放url
              document.body.removeChild(link)//释放标签
            }else{
              navigator.msSaveBlob(blob, fileName)
            }
          })
         
        },
        handleExceed(files, fileList) {
          this.$message.warning(
            `当前限制选择 1 个文件，本次选择了 ${
              files.length
            } 个文件，共选择了 ${files.length + fileList.length} 个文件`
          );
        },
        successUpload(response) {
          const { code, msg } = response
          if(code == 20000){
            this.$notify({
              title: 'Success',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
          }
          
        }
    }
}
</script>

<style>

</style>