const Home = {template : '<home></home>'}
const MainPage = {template: '<mainPage></mainPage>'}
const Customer = {template: '<customer/>'}
const Administrator = {template: '<administrator/>'}
const Manager = {template: '<manager/>'}
const Coach = {template: '<coach/>'}
const Registration = {template : '<registration/>'}
const FacilitiesView = {template: '<facilitiesView/>'}
const DisplayFacility = {template: '<displayFacility/>'}
const EditUser = {template: '<editUser/>'}
const AllUsers = {template: '<allUsers/>'}
const NewFacility = {template: '<newFacility/>'}
const NewContent = {template: '<newContent />'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{path: '/', component: Home},
		{path: '/mainPage', component: MainPage},
		{path: '/customer', component: Customer},
		{path: '/admin', component: Administrator},
		{path: '/manager', component: Manager},
		{path: '/coach', component: Coach},
		{path: '/registration', component: Registration},
		{path: '/facilitiesView', component: FacilitiesView},
		{path: '/displayFacility/:name', component: DisplayFacility},
		{path: '/editUser', component: EditUser},
		{path: '/allUsers', component: AllUsers},
		{path: '/newFacility', component: NewFacility},
		{path: '/newContent', component: NewContent}
	  ]
});

var app = new Vue({
	router,
	el: '#fitpass'
});