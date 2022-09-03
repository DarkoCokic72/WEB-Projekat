Vue.component('allWorkouts', {
	data: function() {
		return {
			workouts: null,
            sportFacilityNameSearch: '',
            dateFrom: '',
            dateTo: '',
            role: localStorage.getItem('role')
		}
	},
	methods: {
		"search": function(e) {
			axios.get("/searchWorkouts",
				{
					contentType: "application/json",
					dataType: "json",
					params: {
						sportFacilityNameSearch: this.sportFacilityNameSearch,
                        dateFrom: this.dateFrom,
                        dateTo: this.dateTo
					}
				})
				.then(response => {
					if (response.data) {
						this.workouts = response.data;
					}
				})
		}
		
	},
	mounted (){
        axios.get("/allWorkouts",{
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
            <div>
                <input v-if="role === 'Customer' || role === 'Coach'" type="text" v-model="sportFacilityNameSearch" placeholder="Naziv sportskog objekta">
                Početni datum:
                <input v-model="dateFrom" type="date">
                Krajnji datum:
                <input v-model="dateTo" type="date">
                <button v-on:click="search">Pretraga</button>
            </div>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Naziv sportskog objekta</th>
                        <th>Tip sportskog objekta</th>
                        <th>Datum prijave treninga</th>
                        <th>Naziv treninga</th>
                        <th>Tip treninga</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.workout.sportFacility.name}}</td>
                        <td>{{w.workout.sportFacility.type}}</td>
                        <td v-if="w.dateTimeOfWorkout.time.minute === 0">{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}0</td>
                        <td v-else-if="w.dateTimeOfWorkout.time.minute < 10">{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:0{{w.dateTimeOfWorkout.time.minute}}</td>
                        <td v-else>{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}</td>
                        <td>{{w.workout.name}}</td>
                        <td v-if="w.workout.type === 'Personal'">Personalni</td>
                        <td v-else>Grupni</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})