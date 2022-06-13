
const FacilitiesView = {template: '<facilitiesView/>'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{path: '/facilitiesView', component: FacilitiesView}
	  ]
});

var app = new Vue({
	router,
	el: '#fitpass'
});