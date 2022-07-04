Vue.component('displayFacility', {
    data: function(){
        return{
            facility: null
        }
    },
    beforeMount(){
        axios.get("/facilityByName?name=" + this.$route.params.name, {})
        .then(response => {
            if(response.data){
                this.facility = response.data;
            }
        })
    },
    mounted(){
        
    },
    template:
    `
    <div>
        <h1>{{facility.name}}</h1>
        <img :src="facility.image"/><br/>
        <h3>Tip: {{facility.type}}</h3>
        <h3>
            Status:
            <span v-if="facility.status">
                <label>Otvoren</label>
            </span>
            <span v-else>
                <label>Zatvoren</label>
            </span>
        </h3>
        <h3>
            Lokacija:
            {{facility.location.city}} {{facility.location.postalCode}},
            {{facility.location.street}} {{facility.location.number}},&nbsp;
            Koordinate:
            {{facility.location.longitude}} {{facility.location.latitude}}
        </h3>
        <h3>
            Ocena:
            {{facility.averageScore}}
        </h3>
        <h1>Raspored treninga:</h1>
        <div>
            <h1>Komentari o sportskom objektu:</h1>
        </div>
    </div>
    `
})