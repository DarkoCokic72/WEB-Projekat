Vue.component('registration',{
    data: function(){
        return{
            username: null,
            password: null,
            name: null,
            surname: null,
            gender: null,
            dateOfBirth: null
        }
    },
    methods:{
    },
    template:`
    <form action="#/" method="post" @submit="checkForm" class="center">
		<table class="smalltable">
			<tr>
				<td>Korisničko ime</td>
				<td><input v-model="username" :disabled="editMode" type="text" name="username"></td>
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
			<tr v-if="renderAll()" >
				<td>Uloga</td>
				<td><select v-model="uloga" name="uloga">
						<option value="Kupac">Kupac</option>
						<option value="Menadzer">Menadžer</option>
						<option value="Dostavljac">Dostavljač</option>
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