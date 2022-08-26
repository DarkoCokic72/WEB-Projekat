Vue.component('allUsers', {
    data: function(){
        return{
            users: null,
            username: '',
            name: '',
            surname: '',
            gender: '',
            collectedPoints: '',
            role: '',
            ascending: false,
            usernameSearch: '',
            nameSearch: '',
            surnameSearch: '',
            sortColumn: '',
            typeFilter: '',
            roleFilter: ''
        }
    },
    computed: {
        filteredUsers: function() {
			if (this.users == null) 
                return null;
			role_filter = this.roleFilter;
			type_filter = this.typeFilter;
			return this.users.filter(function(row) {
				role = row.role;
				type = role === 'Customer' ? row.type : null;
				return role.includes(role_filter) && (type == type_filter || type_filter == '');
			});
		}
    },
    methods:{
        "search": function(e){
            axios.get("/admin/allUsers?nameSearch=" + this.nameSearch + "&surnameSearch=" + this.surnameSearch + "&usernameSearch=" + this.usernameSearch,
				    )
					.then(response => {
						if(response.data)
						{ 
							this.users = response.data;
						}
					})
        },
        "sortTable": function sortTable(col) {
			if (this.sortColumn === col) {
				this.ascending = !this.ascending;
			} else {
				this.ascending = true;
				this.sortColumn = col;
			}

			var ascending = this.ascending;
			this.users.sort(function(a, b) {
				if (a[col] > b[col]) {
					return ascending ? 1 : -1
				} else if (a[col] < b[col]) {
					return ascending ? -1 : 1
				}
				return 0;
			})
		}
    },
    mounted(){
        axios.get("/admin/allUsers", {
			headers: {
			},
			contentType: "application/json",
			dataType: "json",
		})
			.then(response => {
				if (response.data) {
					this.users = response.data;
				}
			})
    },
    template:
    `
        <div>
            <h1>Pregled svih korisnika</h1>
            <div>
                <input type="text" placeholder="Korisničko ime" v-model="usernameSearch">
                <input type="text" placeholder="Ime" v-model="nameSearch">
			    <input type="text" placeholder="Prezime" v-model="surnameSearch">
			    <button v-on:click="search">Pretraga</button>
            </div>
            <div>
                Uloga:
			    <select name="role" v-model="roleFilter">
                    <option></option>
                    <option value="Customer">Kupac</option>
                    <option value="Administrator">Administrator</option>
                    <option value="Manager">Menadžer</option>
                    <option value="Coach">Trener</option>
		        </select>
                Tip korisnika:
                <select name="type" v-model="typeFilter">
                    <option></option>
                    <option>Bronzani</option>
                    <option>Srebrni</option>
                    <option>Zlatni</option>
                </select>

            </div>
            <table id="table">
                <thead>
                    <tr>
                        <th v-on:click="sortTable('username')">
                            Korisničko ime
                        </th>
                        <th v-on:click="sortTable('name')">
                            Ime
                        </th>
                        <th v-on:click="sortTable('surname')">
                            Prezime
                        </th>
                        <th>
                            Pol
                        </th>
                        <th>
                            Uloga
                        </th>
                        <th v-on:click="sortTable('collectedPoints')">
                            Sakupljeni bodovi
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in filteredUsers">
                        <td>{{user.username}}</td>
                        <td>{{user.name}}</td>
                        <td>{{user.surname}}</td>
                        <td v-if="user.gender === 'male'">
                            Muški
                        </td>
                        <td v-else>
                            Ženski
                        </td>
                        <td v-if="user.role === 'Administrator'">
                            Administrator
                        </td>
                        <td v-else-if="user.role === 'Manager'">
                            Menadžer
                        </td>
                        <td v-else-if="user.role === 'Coach'">
                            Trener
                        </td>
                        <td v-else>
                            Kupac
                        </td>
                        <td>{{user.collectedPoints}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    `
})