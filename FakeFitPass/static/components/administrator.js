Vue.component('administrator', {
	data: function() {
		return {
		}
	},
	methods: {
		new: function(val) {
			console.log(val);
			localStorage.setItem('registracijaNovog', val);
		},

		logout() {
			localStorage.setItem('jwt', -1);
			localStorage.setItem("role", "");
		}
	},
	template: `
	<div>
		<h1>Dobro došli administratore!</h1>
		<a href="/#/" v-on:click="logout()">Odjava</a><br/>
		<a href="/#/registration" v-on:click="new(true)">Registracija novog korisnika</a><br/>
		<a href="/#/allUsers">Pregled svih korisnika</a><br/>
		<a href="/#/editUser">Pregled i izmena ličnih podataka</a><br/>
		<a href="/#/newFacility">Novi sportski objekat</a><br/>
		<a href="/#/allWorkouts">Pregled svih treninga</a><br/>
		<a href="/#/administratorCommentsView">Prihvatanje i odbijanje komentara</a><br/>
	</div>                   
`
})