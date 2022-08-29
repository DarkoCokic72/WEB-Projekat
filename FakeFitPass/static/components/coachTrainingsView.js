Vue.component('coachTrainingsView', {
	data: function() {
		return {
			workouts: null
		}
	},
	methods: {
		
		
	},
	mounted (){
        axios.get("/coach/allWorkouts",{
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
                        <th>Tip treninga</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Trajanje treninga(u satima)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.name}}</td>
                        <td v-if="w.type === 'Personal'">Personalni</td>
                        <td v-else>Grupni</td>
                        <td>{{w.sportFacility.name}}</td>
                        <td v-if="w.duration.time.minute === 0">{{w.duration.time.hour}}</td>
                        <td v-else>{{w.duration.time.hour}}:{{w.duration.time.minute}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})