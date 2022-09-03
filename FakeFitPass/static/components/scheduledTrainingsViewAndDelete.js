Vue.component('scheduledTrainingsViewAndDelete', {
	data: function() {
		return {
			workouts: null
		}
	},
	methods: {
		"cancelWorkout": function(id){
            axios.delete("/coach/cancelWorkout?id=" + id,
				    )
					.then(response => {
						if(response.data)
						{ 
                            alert("Uspešno otkazan trening.");
							this.$router.push('/mainPage');
						}
					})
        }
		
	},
	mounted (){
        axios.get("/coach/allScheduledWorkouts",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.workouts = response.data; 
        })
		},
	template: 
    `
        <div>
            <h1>Pregled zakazanih personalnih treninga</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Ime i prezime vežbača</th>
                        <th>Naziv treninga</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Datum i vreme treninga</th>
                        <th>Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.name}} {{w.surname}}</td>
                        <td>{{w.workout.name}}</td>
                        <td>{{w.workout.sportFacility.name}}</td>
                        <td v-if="w.dateTimeOfWorkout.time.minute === 0">{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}0</td>
                        <td v-else>{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}</td>
                        <td v-if="w.isVisible === false"><button @click="cancelWorkout(w.id)">Otkaži</button></td>
                    </tr>
                </tbody>
            </table>
            <h3>* moguće je otkazati samo treninge 2 dana unapred</h3>
        </div>
	`
})