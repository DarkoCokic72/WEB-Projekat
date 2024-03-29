Vue.component('manager', {
	data: function() {
		return {
		}
	},
	methods: {
		new: function(val) {
			localStorage.setItem('registracijaNovog', val);
		},

		logout() {
			localStorage.setItem('jwt', -1);
			localStorage.setItem("role", "");
		}
	},
	mounted() {
	},
	template: `
	<div>
		<h1>Dobro došli menadžeru!</h1>
		<a href="/#/" v-on:click="logout()">Odjava</a><br/>
		<a href="/#/editUser">Pregled i izmena ličnih podataka</a><br/>
		<a href="/#/newContent">Dodavanje novog sadržaja</a><br/>
		<a href="/#/displayContentForEdit">Prikaz sadržaja za izmenu</a><br/>
		<a href="/#/managerTrainingsView">Pregled sadržaja u sportskom objektu</a><br/>
		<a href="/#/managerSportFacilityView">Pregled sportskog objekta</a><br/>
		<a href="/#/allWorkouts">Pregled svih treninga</a><br/>
		<a href="/#/defineTerm">Definisanje termina</a><br/>
		<a href="/#/managerCommentsView">Pregled odobrenih i odbijenih komentara</a><br/>
	</div>                   
`
})