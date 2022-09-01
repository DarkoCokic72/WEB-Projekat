Vue.component('customer', {
	data: function() {
		return {
			ime: null
		}
	},
	methods: {
		novi: function(val) {
			localStorage.setItem('registracijaNovog', val);
		},

		logout() {
			localStorage.setItem('jwt', -1);
			localStorage.setItem("role", "");
		}
	},
	template: `
	<div>
		<h1>Dobro došli vežbaču!</h1>
		<a href="/#/" v-on:click="logout()">Odjava</a><br/>
		<a href="/#/editUser">Pregled i izmena ličnih podataka</a><br/>
		<a href="/#/customerTrainingsView">Pregled svih treninga</a><br/>
		<a href="/#/schedulingTraining">Zakazivanje personalnih treninga</a><br/>     		
	 </div>      
		      
`
})