Vue.component('createMembership', {
	data: function() {
		return {
			membershipId: '',
            memberships: null,
            membership: null,
            isSelected: false
		}
	},
	methods: {
		"displayMembership": function() {
            if(this.membershipId === ''){
                alert("Niste odabrali članarinu!");
            }
			axios.get("/customer/displayMembership?membershipId=" + this.membershipId,
				{
					contentType: "application/json",
					dataType: "json"
				})
				.then(response => {
					if (response.data) {
						this.membership = response.data;
                        this.isSelected = true;
					}
				})
		},
        
        "back": function() {
			this.isSelected = false;
		},

        "buyMembership": function() {
			axios.post("/customer/buyMembership?membershipId=" + this.membershipId,
				{
					contentType: "application/json",
					dataType: "json"
				})
				.then(response => {
					if (response.data) {
						alert("Članarina je uspešno uplaćena.");
                        this.$router.push('/mainPage');
					}
				})
		},

        submit : function() {
			if (this.code === "") {
				alert("Morate uneti promo kod!");
				return;
			}
			
			axios
			.post('/customer/checkPromoCode', {
				promoCode: this.code,
				membershipId: this.membershipId
			})
			.then(response => { 
				if (response.data === "") {
					alert("Kod nije ispravan!");
					return;
				}
				this.promoCodeUsed = true;
				this.membership = response.data; 
				alert("Dobili ste popust!");
			});
		}
		
	},
	mounted (){
        axios.get("/customer/allMemberships",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.memberships = response.data;
        })
		},
	template: 
    `
        <div>
            <div v-if="isSelected === false">
                <h2>Odaberite članarinu</h2>
                <label>Članarina(tip članarine - cena(u dinarima) - broj termina):</label>
                <select name="type" v-model="membershipId">
                    <option></option>
                    <option v-for="membership in memberships" :value="membership.membershipID" v-if="membership.type === 'Monthly'">Mesečna - {{membership.price}} - {{membership.numberOfAppointments}}</option>
                    <option v-for="membership in memberships" :value="membership.membershipID" v-if="membership.type === 'Year'">Godišnja - {{membership.price}} - {{membership.numberOfAppointments}}</option>
                </select>
                <button @click="displayMembership()">Prikaži</button>
            </div>
            <div v-if="isSelected === true">
            <h2>Odabrali ste članarinu:</h2>
            <div v-if="membership.type === 'Monthly'">
            <label>Tip članarine:</label>
            Mesečna<br/>
            </div>
            <div v-if="membership.type === 'Year'">
            <label>Tip članarine:</label>
            Godišnja<br/>
            </div>
            <label>Cena članarine(u dinarima):</label>
            {{membership.price}}<br/>
            <label>Broj termina članarine:</label>
            {{membership.numberOfAppointments}}<br/><br/>
            <div v-if="!promoCodeUsed">
            <label>Promo kod (opciono)</label>
            <input type="text" v-model="code">&nbsp;<button v-on:click="submit()">Iskoristi kod</button>
            </div><br/><br/>
            <button @click="buyMembership()">Plati</button> &nbsp;
            <button @click="back()">Nazad na odabir</button>
            </div>
        </div>
	`
})