Vue.component('facilitiesView', {
    data: function(){
        return {
            facilities: null,
            ascending: false,
            sortColumn: '',
            type_filter: '',
            status_filter: '',
            name_search:'',
            type_search:'',
            score_search:'',
            location_search:''
        }
    },
    computed: {
		filteredFacilities: function () {
			if(this.facilities == null) 
                return null;
			type_filter = this.type_filter
			status_filter = this.status_filter

			return this.facilities.filter(function(row){
				type = row.type
				statuss = row.status ? 'Otvoren' : 'Zatvoren'
				if (status_filter == '')
					statuss = status_filter
				return type.includes(type_filter) &&  statuss == status_filter    
			});
	    }
	},
    methods: {
        "search": function(){
			console.log(this.name_search)
            axios.get("/allFacilities?nameSearch=" + this.name_search + "&locationSearch=" + this.location_search + "&typeSearch=" + this.type_search + "&scoreSearch=" + this.score_search, {})               
            .then(response => {
                this.facilities = response.data; 
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
			this.facilities.sort(function(a, b) {
				
				if (startval === 'location.street')
				{
					col = 'street'
					a = a['location']
					b = b['location']
				}
				
				if (a[col] > b[col]) {
				  return ascending ? 1 : -1
				} else if (a[col] < b[col]) {
				  return ascending ? -1 : 1
				}
				return 0;
			  })
	    },
        "displayFacility": function displayFacility(name) {

			this.$router.push('/displayFacility/' + name);
			
		}
    },
    mounted() {
        axios.get("/allFacilities",{
                contentType:"application/json",
                dataType:"json",
			  })
			.then(response => {
					this.facilities = response.data; 
			})
    },
    template:  `
        <div> 
            <div>
                Naziv:
                <input type = "text" v-model = "name_search" name = "nameSearch">
                Lokacija:
                <input type = "text" v-model = "location_search" name = "locationSearch">
                Tip:
                <select name="typeSearch" v-model="type_search">
					<option></option>
					<option>Gym</option>
					<option>Pool</option>
					<option>SportCentre</option>
					<option>DanceStudio</option>
			    </select>
                Ocena:
                <select name="scoreSearch" v-model="score_search">
					<option></option>
					<option>4-5</option>
					<option>3-5</option>
					<option>2-5</option>
					<option>1-5</option>
			    </select>
                <button v-on:click="search">Pretraga</button>
            </div>
            <div>
			Tip sportskog objekta:
			<select name="type" v-model="type_filter">
				<option></option>
				<option>Gym</option>
				<option>Pool</option>
				<option>SportCentre</option>
                <option>DanceStudio</option>
			</select>
			Status:
			<select name="status" v-model="status_filter">
				<option></option>
				<option>Otvoren</option>
				<option>Zatvoren</option>
			</select>
		    </div>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th v-on:click="sortTable('name')">Naziv</th>
                        <th>Tip objekta</th>
                        <th v-on:click="sortTable('location.street')">Lokacija</th>
                        <th>Logo</th>
                        <th>Radno vreme</th>
                        <th>Status</th>
                        <th v-on:click="sortTable('averageScore')">Prosečna ocena</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="facility in filteredFacilities" v-if="facility.status" v-on:click="displayFacility(facility.name)">
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
                        <td>{{facility.startTime.time.hour}} - {{facility.endTime.time.hour}}</td>
                        <td class="open">Otvoren</td>
                        <td>{{facility.averageScore}}/5</td>
                    </tr>
                </tbody>
            </table>
        </div>
    `
})