Vue.component('registration', {
	data: function() {
		return {
			username: null,
			password: null,
			name: null,
			surname: null,
			gender: null,
			dateOfBirth: null,
			editMode: false,
			role: 'Customer',
			role: localStorage.getItem('role'),
			jwt: localStorage.getItem('jwt'),
			registracijaNovog: localStorage.getItem('registracijaNovog'),
			aktuelniRestoran: localStorage.getItem("aktuelniRestoran")
		}
	},
	methods: {
		checkRegistrationResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešna registracija.");
				e.preventDefault();
			}
			else {
				alert("Uspešno registrovan korisnik.");
				if(this.jwt === "-1" || this.jwt === "-2") {
					e.target.submit();
				}
				else {
					this.$router.push('/mainPage');
				}
			}
		},

		checkEditResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešna izmena.");
			}
			else {
				alert("Uspešna izmena.");
				this.$router.push('/mainPage');
			}
		},

		checkForm: function(e) {
			e.preventDefault();
			if (!this.username || !this.password || !this.name || !this.surname ||
				!this.gender || !this.dateOfBirth || !this.role) {
				alert("Morate popuniti sva polja")
			}
			else if (localStorage.getItem('registracijaNovog') === "true"){
				axios
					.post('/registration', {
						username: this.username,
						password: this.password,
						name: this.name,
						surname: this.surname,
						gender: this.gender,
						dateOfBirth: this.dateOfBirth,
						role: this.role
					}, {params: {restoran: this.aktuelniRestoran}})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
		},
		renderAll: function() {
			return localStorage.getItem("aktuelniRestoran") === "null" && localStorage.getItem('role')==='Administrator' && localStorage.getItem('registracijaNovog')==="true";
		}
	},

	mounted() {
	
		if (localStorage.getItem('registracijaNovog') === "false") {
			this.editMode = true
			axios
				.post('/izmenaPodataka', {}, { params: { jwt: this.jwt } })
				.then(response => (this.izmenaResponse(response)));
		}
	},

	template: `
	<form action="#/" method="post" @submit="checkForm" class="center">
		<table class="smalltable">
			<tr>
				<td>Korisničko ime</td>
				<td><input v-model="username" type="text" name="username"></td>
			</tr>
			<tr>
				<td>Lozinka</td>
				<td><input v-model="password" type="text" name="password"></td>
			</tr>
			<tr>
				<td>Ime</td>
				<td><input v-model="name" type="text" name="name"></td>
			</tr>
			<tr>
				<td>Prezime</td>
				<td><input v-model="surname" type="text" name="surname"></td>
			</tr>
			<tr>
				<td>Pol</td>
				<td><select v-model="gender" name="gender">
						<option value="Muski">Muški</option>
						<option value="Zenski">Ženski</option>
						</select>
				</td>
			</tr>
			<tr>
				<td>Datum rođenja</td>
				<td><input v-model="dateOfBirth" type="date" name="dateOfBirth"></td>
			</tr>
			<tr>
				<td>Uloga</td>
				<td><select v-model="role" name="role">
						<option value="Customer">Customer</option>
						<option value="Manager">Manager</option>
						<option value="Coach">Coach</option>
						</select>
				</td>
			</tr>	
			<tr>
				<td><input type="submit" value="Registruj"></td>
			</tr>
		</table>
	</form>             
`
})