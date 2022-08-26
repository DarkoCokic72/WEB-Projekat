Vue.component('customerTrainingsView', {
	data: function() {
		return {
			workouts: null
		}
	},
	methods: {
		
		
	},
	mounted (){
        axios.get("/customer/allWorkouts",{
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
            <h1>Pregled svih treninga</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Naziv treninga</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Datum treniranja u proteklih mesec dana</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.workout.name}}</td>
                        <td>{{w.workout.sportFacility.name}}</td>
                        <td>{{w.timeOfCheckIn.date.day}}.{{w.timeOfCheckIn.date.month}}.{{w.timeOfCheckIn.date.year}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})