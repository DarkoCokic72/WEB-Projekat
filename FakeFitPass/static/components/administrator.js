Vue.component('administrator', {
	data: function() {
		return {
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
	template: `
	<div>
		<h1>Dobro došli admine!</h1>       
	</div>                   
`
})