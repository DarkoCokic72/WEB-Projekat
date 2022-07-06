Vue.component('allUsers', {
    data: function(){
        return{
            users: null,
            usernameSearch: '',
            nameSearch: '',
            surnameSearch: '',
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
            <h1>Svi korisnici</h1>
            <div>
                <input type="text" placeholder="Korisničko ime" v-model="usernameSearch">
                <input type="text" placeholder="Ime" v-model="nameSearch">
			    <input type="text" placeholder="Prezime" v-model="surnameSearch">
			    <button v-on:click="search">Pretraga</button>
            </div>
            <table id="table">
                <thead>
                    <tr>
                        <th v-for="(col, index) in columns">
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