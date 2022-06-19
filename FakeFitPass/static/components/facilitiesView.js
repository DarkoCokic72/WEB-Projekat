Vue.component('facilitiesView', {
    data: function(){
        return {
            facilities: null,
            ascending: false,
            status_filter: '',
            name_search:'',
            type_search:'',
            score_search:'',
            location_search:''
        }
    },
    methods: {
        "search": function(e){
            axios.get("/allFacilities", {
                params: {
                    name_search: this.name_search,
                    type_search: this.type_search,
                    score_search: this.score_search,
                    location_search: this.location_search
                }
            }).then(response => {
                this.facilities = response.data; 
            })
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
					<option>Personal Workout</option>
					<option>Group Workout</option>
					<option>Sauna</option>
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
            <table id = "tabela" border = "1">
                <thead>
                    <tr>
                        <th>Naziv</th>
                        <th>Tip objekta</th>
                        <th>Lokacija</th>
                        <th>Logo</th>
                        <th>Sadrzaj</th>
                        <th>Radno vreme</th>
                        <th>Status </th>
                        <th>Prosecna ocena</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for = "facility in facilities">
                        <td>{{facility.name}}</td>
                        <td>{{facility.type}}</td>
                        <td>{{facility.location.postalCode}} <br />
                            {{facility.location.city}} <br />
                            {{facility.location.street}} {{facility.location.number}} <br />
                            {{facility.location.longitude}} {{facility.location.latitude}}
                        </td>
                        <td>
                            <img :src = "facility.image" />
                        </td>
                        <td> {{facility.content}}</td>
                        <td>{{facility.startTime.time.hour}} - {{facility.endTime.time.hour}}</td>
                        <td class = "open">Otvoren</td>
                        <td>{{facility.averageScore}} /5</td>
                    </tr>
                </tbody>
            </table>
        </div>
    `
})