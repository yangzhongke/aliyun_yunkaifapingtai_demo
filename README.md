# aliyun_yunkaifapingtai_demo
阿里云云开发平台演示案例

阿里云云开发平台地址：[https://workbench.aliyun.com/](https://workbench.aliyun.com/?source=5176.11533457&userCode=n9nebfal)

## 录音文件识别（极速版）开通方法 ##
1. 阿里云：产品→人工智能→智能语音交互→录音文件识别→【立即开通】，【录音文件识别（极速版）】选择【商用】。
2. 进入控制台，【创建项目】，项目类型选【仅语音识别】。在【语音识别】中点击【配置】→【没有音频测试集】，选择【非电话】→【英文】→【确认使用】。把【项目Appkey】拷出来备用，这个是用来在代码中指定用哪个“项目”（识别模型）。
3. 在编程调用之前，需要首先配置RAM，这样只有指定的用户才能调用接口，避免接口被盗用浪费钱。右上角点击【访问控制】，新建一个用户，假如为“voiceAI”，然后设定“编程访问”。要保存下来AccessKey ID、AccessKey Secret，不要泄露!
4. 选中用户，点击【添加权限】，选中权限“AliyunNLSSpeechServiceAccess”.

## 代码使用方法： ##
1. 把源代码从github上把“上课代码.zip”下载下来，解压到硬盘。 
2. 在“阿里云-云开发平台” 上新建一个应用，语言选择java，不选择模板。
3. 把代码中MainController.java粘贴到云开发IDE上，把resources下的static文件夹粘贴到云开发IDE对应目录下。
4. 修改RAM、录音文件识别相关的配置.

aliyun.ai.speech.appKey=xxx
aliyun.ai.speech.accessKey_ID=xxx
aliyun.ai.speech.accessKey_Secret=xxx

6. 按照视频中讲的运行及部署即可。
