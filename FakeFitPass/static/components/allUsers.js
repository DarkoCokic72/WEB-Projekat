Vue.component('allUsers', {
    data: function(){
        return{
            users: null,
            columns: [{ name: "username" }, { name: "name" }, { name: "surname" }],
			names: ["Korisničko ime", "Ime", "Prezime"]
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