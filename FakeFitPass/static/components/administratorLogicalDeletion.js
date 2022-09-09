Vue.component('administratorLogicalDeletion', {
	data: function() {
		return {
			workouts: null,
            facilities: null
		}
	},
	methods: {
		"deleteWorkout": function(id){
            axios.delete("/admin/deleteWorkout?id=" + id,
				    )
					.then(response => {
						if(response.data)
						{ 
                            alert("Uspešno obrisan trening.");
							window.location.reload();
						}
					})
        },

        "deleteFacility": function(name){
            axios.delete("/admin/deleteFacility?name=" + name,
				    )
					.then(response => {
						if(response.data)
						{ 
                            alert("Uspešno obrisan sportski objekat.");
							window.location.reload();
						}
					})
        }
	},
	mounted (){
        axios.get("/admin/allWorkouts",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.workouts = response.data;
        })

        axios.get("/admin/allSportFacilities",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.facilities = response.data;
        })
		},
	template: 
    `
        <div>
            <h1>Logičko brisanje</h1>
            <h2>Treninzi</h2>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Naziv treninga</th>
                        <th>Tip treninga</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Tip sportskog objekta</th>
                        <th>Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="workout in workouts">
                        <td>{{workout.name}}</td>
                        <td v-if="workout.type === 'Personal'">Personalni</td>
                        <td v-else>Grupni</td>
                        <td>{{workout.sportFacility.name}}</td>
                        <td>{{workout.sportFacility.type}}</td>
                        <td><button @click="deleteWorkout(workout.id)">Obriši</button></td>
                    </tr>
                </tbody>
            </table>
            <h2>Sportski objekti</h2>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Naziv sportskog objekta</th>
                        <th>Tip sportskog objekta</th>
                        <th>Lokacija</th>
                        <th>Logo</th>
                        <th>Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="facility in facilities">
                        <td>{{facility.name}}</td>
                        <td>{{facility.type}}</td>
                        <td>{{facility.location.postalCode}} <br />
                            {{facility.location.city}} <br />
                            {{facility.location.street}} {{facility.location.number}} <br />
                            {{facility.location.longitude}} {{facility.location.latitude}}
                        </td>
                        <td>
                            <img :src="facility.image"/>
                        </td>
                        <td><button @click="deleteFacility(facility.name)">Obriši</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})