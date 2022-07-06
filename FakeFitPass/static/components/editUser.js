Vue.component('editUser',{
    data: function(){
        return{
            username: null,
			password: null,
			name: null,
			surname: null,
			gender: null,
			dateOfBirth: null,
            role: localStorage.getItem('role'),
			jwt: localStorage.getItem('jwt')
        }
    },
    methods: {
        checkEditResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešna izmena.");
			}
			else {
				alert("Uspešna izmena.");
                this.$router.push('/mainPage');
			}
		},
        checkForm: function(e){
            e.preventDefault();
            if(this.gender === "Muski"){
                this.gender = "male";
            }else{
                this.gender = "female";
            }
            axios.put('/editData?jwt=' + this.jwt, {
						username: this.username,
						password: this.password,
						name: this.name,
						surname: this.surname,
						gender: this.gender,
						dateOfBirth: this.dateOfBirth,
						role: this.role
					})
					.then(response => {
                        this.checkEditResponse(response, e);
                    });
        },
        editResponse: function(response){
			this.username = response.data.username;
			this.password = response.data.password;
			this.name = response.data.name;
			this.surname = response.data.surname;
            if(response.data.gender === "male"){
                this.gender = "Muski";
            }else{
                this.gender = "Zenski";
            }
			this.dateOfBirth = response.data.dateOfBirth;
            if(response.data.role === "Manager"){
                this.role = "Menadžer";
            }else if(response.data.role === "Coach"){
                this.role = "Trener";
            }else if(response.data.role === "Customer"){
                this.role = "Kupac";
            }
		}
    },
    mounted() {
			axios
				.get('/obtainData?jwt=' + this.jwt, {})
				.then(response => (this.editResponse(response)));
	},
    template:
    `
    <form action="#/" @submit="checkForm" method="put" class="center">
        <table class="smalltable">
            <tr>
                <td>Korisničko ime</td>
                <td><input v-model="username" type="text" name="username" disabled></td>
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
                <td><select v-model="gender" name="gender" selected="selected">
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
                <td>
                    <input v-model="role" name="role" type="text" disabled>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Izmeni"></td>
            </tr>
        </table>
    </form>  
    `
})