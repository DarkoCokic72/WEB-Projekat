Vue.component('manager', {
	data: function() {
		return {
			kupci: null,
			restoran: null,
			jwt: localStorage.getItem("jwt"),
			ime: null
		}
	},
	methods: {
		novi: function(val) {
			localStorage.setItem('registracijaNovog', val);
		},

		odjava() {
			localStorage.setItem('jwt', -1);
			localStorage.setItem("role", "");
		}
	},
	mounted() {
	},
	template: `
	<div>
		<h1>Dobro došli menadzeru!</h1>
	</div>                   
`
})