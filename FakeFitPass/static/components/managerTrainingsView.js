Vue.component('managerTrainingsView', {
	data: function() {
		return {
			workouts: null
		}
	},
	methods: {
		
		
	},
	mounted (){
        axios.get("/manager/allWorkouts",{
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
            <h1>Pregled sadržaja u sportskom objektu</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Naziv treninga</th>
                        <th>Tip treninga</th>
                        <th>Naziv sportskog objekta</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.name}}</td>
                        <td v-if="w.type === 'Personal'">Personalni</td>
                        <td v-else>Grupni</td>
                        <td>{{w.sportFacility.name}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})