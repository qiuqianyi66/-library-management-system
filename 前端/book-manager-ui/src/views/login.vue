<template>
  <div class="login-wrapper">
    <div class="login-left">
      <div class="login-left-content">
        <h1 class="login-left-title">高校图书管理系统</h1>
        <p class="login-left-subtitle">Smart Library Management System</p>
        <div class="login-left-decorations">
          <div class="deco-circle"></div>
          <div class="deco-square"></div>
          <div class="deco-triangle"></div>
        </div>
      </div>
    </div>
    <div class="login-right">
      <div class="login-right-inner">
        <div class="login-logo">
          <img src="@/assets/logo/logo.png" alt="logo" />
        </div>
        <h3 class="login-welcome">欢迎回来</h3>
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="账号"
            >
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          <el-form-item prop="code" v-if="captchaEnabled">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              style="width: 63%"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
        </el-form>
        <div class="login-copyright">© 2025 高校图书管理系统</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({path: "/bookmanager/reader" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login-wrapper {
  display: flex;
  height: 100vh;
  width: 100%;
}

.login-left {
  flex: 1;
  max-width: 45%;
  background: linear-gradient(135deg, #0f172a 0%, #1e3a5f 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.login-left-content {
  text-align: center;
  z-index: 1;
}

.login-left-title {
  font-size: 28px;
  font-weight: bold;
  color: #ffffff;
  margin: 0 0 12px 0;
  letter-spacing: 2px;
}

.login-left-subtitle {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.6);
  margin: 0 0 40px 0;
  letter-spacing: 1px;
}

.login-left-decorations {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
}

.deco-circle {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.15);
}

.deco-square {
  width: 50px;
  height: 50px;
  border: 2px solid rgba(255, 255, 255, 0.1);
  transform: rotate(45deg);
}

.deco-triangle {
  width: 0;
  height: 0;
  border-left: 25px solid transparent;
  border-right: 25px solid transparent;
  border-bottom: 50px solid rgba(255, 255, 255, 0.08);
}

.login-right {
  flex: 1;
  max-width: 55%;
  background: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-right-inner {
  width: 400px;
}

.login-logo {
  text-align: center;
  margin-bottom: 10px;
  img {
    height: 50px;
    vertical-align: middle;
  }
}

.login-welcome {
  text-align: center;
  font-size: 22px;
  color: #1e293b;
  margin: 0 0 30px 0;
  font-weight: 500;
}

.login-form {
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.login-code-img {
  height: 38px;
}

.login-copyright {
  text-align: center;
  color: #94a3b8;
  font-size: 12px;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .login-left {
    display: none;
  }
  .login-right {
    max-width: 100%;
    flex: 1;
  }
  .login-right-inner {
    width: 90%;
    max-width: 400px;
  }
}
</style>
