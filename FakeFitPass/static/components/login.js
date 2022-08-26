Vue.component('login', {
	data: function() {
		return {
			username: null,
			password: null,
		}
	},


	methods: {
		checkResponse: function(response, event) {
			if (JSON.parse(JSON.stringify(response.data))[0] === "-1") {
				alert("Pogrešni kredencijali.")
			}
			else {
				localStorage.setItem('jwt', response.data.jwt);
				localStorage.setItem("role", response.data.role);
				event.target.submit();
			}
		},
		checkForm: function(e) {
			e.preventDefault();
			axios
				.post('/login', {}, { params: { username: this.username, password: this.password } }
				)
				.then(response => (this.checkResponse(response, e)));


		}
	},

	mounted() {
		localStorage.setItem("role", '');
		localStorage.setItem("jwt", '-1');
		localStorage.setItem("registracijaNovog", true);
	},

	template: `
	<div>
		<h1>Dobro došli!</h1>
		<div>
			<form @submit="checkForm" action="#/mainPage" method="post" class="center">
				<input class="textinput" v-model="username" type="text" placeholder="Uneti korisničko ime" id="username" name="username"><br/>
				<input class="textinput" v-model="password" type="password" placeholder="Uneti lozinku" id="password" name="password"><br/>
				<button class="textinput" type="submit">Prijava</button>
				<a class="textinput" href="/#/registration">Registracija</a>
			</form>
		</div>
	</div>
`
})