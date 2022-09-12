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
			roleCustomer: 'Customer',
			role: localStorage.getItem('role'),
			jwt: localStorage.getItem('jwt'),
			registracijaNovog: localStorage.getItem('registracijaNovog'),
			currentFacility: localStorage.getItem("currentFacility")
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
		checkForm: function(e) {
			e.preventDefault();
			if (!this.username || !this.password || !this.name || !this.surname ||
				!this.gender || !this.dateOfBirth || !this.role) {
				alert("Morate popuniti sva polja!")
			}else if(this.password.length < 8){
				alert("Password mora sadržati najmanje 8 karaktera!");
			}else if(localStorage.getItem('registracijaNovog') === "true"){
				if(this.role === "Customer"){
					this.role = "Customer";
				}else if(this.role === 'Coach'){
					this.role = 'Coach';
				}
				else if(localStorage.getItem("currentFacility") !== "null"){
					this.role = "Manager";
				}
				if(this.gender === "Muski"){
					this.gender = "male";
				}else{
					this.gender = "female";
				}
				axios
					.post('/registration', {
						username: this.username,
						password: this.password,
						name: this.name,
						surname: this.surname,
						gender: this.gender,
						dateOfBirth: this.dateOfBirth,
						role: this.role
					}, {params: {facility: this.currentFacility}})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
		},
		renderAll: function() {
			return localStorage.getItem("currentFacility") !== "null" && localStorage.getItem('role') === 'Administrator' && localStorage.getItem('registracijaNovog') === "true";
		}
	},
	mounted() {

	},

	template: `
	<form action="#/" method="post" @submit="checkForm" class="center">
		<table class="smalltable">
			<tr>
				<td>Korisničko ime</td>
				<td><input v-model="username" type="text" name="username" :disabled="editMode"></td>
			</tr>
			<tr>
				<td>Lozinka</td>
				<td><input v-model="password" type="password" name="password"></td>
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
			<tr v-if="renderAll()">
				<td>Uloga</td>
				<td>
					<select v-model="role" name="role">
						<option value="Coach">Trener</option>
						<option value="Manager">Menadžer</option>
					</select>
				</td>
			</tr>
			<tr v-else>
				<td>Uloga</td>
				<td>
					<select v-model="role" name="role">
						<option value="Customer">Kupac</option>
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