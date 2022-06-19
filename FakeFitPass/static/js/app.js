
const Home = {template : '<home></home>'}
const MainPage = {template: '<mainPage></mainPage>'}
const Customer = {template: '<customer />'}
const Administrator = {template: '<administrator />'}
const Manager = {template: '<manager />'}
const Coach = {template: '<coach />'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{path: '/', component: Home},
		{path: '/mainPage', component: MainPage},
		{path: '/customer', component: Customer},
		{path: '/admin', component: Administrator},
		{path: '/manager', component: Manager},
		{path: '/coach', component: Coach}
	  ]
});

var app = new Vue({
	router,
	el: '#fitpass'
});