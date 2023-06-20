import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
// import './permission' // permission
Vue.use(plugins)

Vue.config.productionTip = false
Vue.prototype.$store = store

App.mpType = 'app'


import util from "./utils/util.js"
Vue.prototype.$toast = util.toast;

import cacheUtil from "./utils/cacheUtil.js"
Vue.prototype.$dataLocal = cacheUtil.data_local;
Vue.prototype.$dataSession = cacheUtil.data_session;

import storage from "./utils/storage.js"
Vue.prototype.$storage = util.storage;

const prePage = ()=>{
	let pages = getCurrentPages();
	let prePage = pages[pages.length - 2];
	// #ifdef H5
	return prePage;
	// #endif
	return prePage.$vm;
}
const json = type=>{
	//模拟异步请求数据
	return new Promise(resolve=>{
		setTimeout(()=>{
			resolve(Json[type]);
		}, 500)
	})
}
// 可能要加msg
Vue.prototype.$api = {json, prePage};

import $mRouter from './utils/router';
Vue.prototype.$mRouter = $mRouter;
const app = new Vue({
  ...App
})
app.$mount()