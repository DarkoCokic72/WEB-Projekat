Vue.component('allUsers', {
    data: function(){
        return{
            users: null,
            usernameSearch: '',
            nameSearch: '',
            surnameSearch: '',
            sortColumn: '',
            columns: [{ name: "username" }, { name: "name" }, { name: "surname" }],
			names: ["Korisničko ime", "Ime", "Prezime"]
        }
    },
    methods:{
        "search": function(e){
            axios.get("/allUsers?nameSearch=" + this.nameSearch + "&surnameSearch=" + this.surnameSearch + "&usernameSearch=" + this.usernameSearch,
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
        axios.get("/allUsers", {
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
            <table id="table">
                <thead>
                    <tr>
                        <th v-for="(col, index) in columns" v-on:click="sortTable(col.name)">
                            {{names[index]}}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in users">
                        <td v-for="col in columns">
                            {{user[col.name]}}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    `
})