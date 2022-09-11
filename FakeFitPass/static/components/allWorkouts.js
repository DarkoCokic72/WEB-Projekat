Vue.component('allWorkouts', {
	data: function() {
		return {
			workouts: null,
            sportFacilityNameSearch: '',
            dateFrom: '',
            dateTo: '',
            role: localStorage.getItem('role'),
            sortColumn: '',
            typeFacilityFilter: '',
            typeWorkoutFilter: '',
            name: ''
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
		},

        "sortTable": function sortTable(col) {
			if (this.sortColumn === col) {
				this.ascending = !this.ascending;
			} else {
				this.ascending = true;
				this.sortColumn = col;
			}
			startval = col.slice()
			var ascending = this.ascending;
			this.workouts.sort(function(a, b) {
				if (startval === 'workout.sportFacility.name')
				{
					col = 'name'
					a = a['workout']
					b = b['workout']
                    a = a['sportFacility']
                    b = b['sportFacility']
				}
                if (startval === 'dateTimeOfWorkout.date')
				{

					col = 'date'
					a = a['dateTimeOfWorkout']
					b = b['dateTimeOfWorkout']
				}
				if (a[col] > b[col]) {
					return ascending ? 1 : -1
				} else if (a[col] < b[col]) {
					return ascending ? -1 : 1
				}
				return 0;
			})
		},

        "filter": function(e) {
			axios.get("/filterWorkouts",
				{
					contentType: "application/json",
					dataType: "json",
					params: {
						typeFacilityFilter: this.typeFacilityFilter,
                        typeWorkoutFilter: this.typeWorkoutFilter
					}
				})
				.then(response => {
					if (response.data) {
						this.workouts = response.data;
					}
				})
		},

        "sortDates": function() {
			axios.get("/sortDates",
				{
					contentType: "application/json",
					dataType: "json"
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
                <button v-on:click="search">Pretraži</button>
            </div>
            <div>
                <label v-if="role === 'Customer' || role === 'Coach'">Tip sportskog objekta:</label>
                <select name="type" v-model="typeFacilityFilter" v-if="role === 'Customer' || role === 'Coach'">
                    <option></option>
                    <option value="Gym">Teretana</option>
                    <option value="Pool">Bazen</option>
                    <option value="SportCentre">Sportski centar</option>
                    <option value="DanceStudio">Plesni studio</option>
                </select>
                <label>Tip treninga:</label>
                <select name="typeWorkout" v-model="typeWorkoutFilter">
                    <option></option>
                    <option value="Group">Grupni</option>
                    <option value="Personal">Personalni</option>
                    <option value="Gym">Teretana</option>
                </select>
                <button v-on:click="filter">Filtriraj</button>
		    </div>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th @click="sortTable('workout.sportFacility.name')" class="cursor">Naziv sportskog objekta</th>
                        <th>Tip sportskog objekta</th>
                        <th @click="sortDates()" class="cursor">Datum prijave treninga</th>
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