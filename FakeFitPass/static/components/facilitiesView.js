Vue.component('facilitiesView', {
    data: function(){
        return {
            facilities: null,
            ascending: false,
            status_filter: ''
        }
    },
    computed: {},
    mounted() {
        axios.get("/allFacilities",{
			headers: {
			  },
			  contentType:"application/json",
			dataType:"json",
			  })
			.then(response => {
				if(response.data)
				{ 
					this.facilities = response.data;
                    console.log("response.data")
				} else {
                    console.log("Nisam nasao nista")
                }
			})
    },
    template:  `
        <div> 
        

        <table id = "tabela" border = "1">
            <thead>
                <tr>
                    <th>Naziv</th>
                    <th>Tip objekta</th>
                    <th>Lokacija</th>
                    <th>Logo</th>
                    <th>Prosecna ocena</th>
                    <th>Radno vreme</th>
                    <th>Status </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for = "(facility, idx) in facilities">
                    <td>{{facility.name}}</td>
                    <td>{{facility.type}}</td>
                    <td>{{facility.location.postalCode}} <br>
                        {{facility.location.city}} <br>
                        {{facility.location.street}}, {{facility.location.number}} <br>
                        {{facility.location.longitude}}, {{facility.location.latitude}}
                    </td>
                    <td>
                        <img : src = "facility.image" />
                    </td>
                    <td v-if = "facility.averageScore >= 1"> {{facility.averageScore}} / 5</td>
                    <td v-else > Nije ocenjen</td>
                    <td>{{facility.startTime}} - {{facility.endTime}}</td>
                    <td class = "open">Otvoren</td>
                </tr>

                <tr v-for = "(facility, idx) in facilities">
                    <td>{{facility.name}}</td>
                    <td>{{facility.type}}</td>
                    <td>{{facility.location.postalCode}} <br>
                        {{facility.location.city}} <br>
                        {{facility.location.street}}, {{facility.location.number}} <br>
                        {{facility.location.longitude}}, {{facility.location.latitude}}
                    </td>
                    <td>
                        <img : src = "facility.image" />
                    </td>
                    <td v-if = "facility.averageScore >= 1"> {{facility.averageScore}} / 5</td>
                    <td v-else > Nije ocenjen</td>
                    <td>{{facility.startTime}} - {{facility.endTime}}</td>
                    <td class = "close">Zatvoeno</td>
                </tr>
            </tbody>
        </table>
        </div>
    `
})