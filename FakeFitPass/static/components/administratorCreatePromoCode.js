Vue.component('administratorCreatePromoCode', {
	data: function() {
		return {
			startDate: null,
			endDate: null,
		    quantity: null,
		    discount: null
		}
	},
	methods: {
		createPromoCode : function() {
			if (this.startTime === null || this.endTime === null || this.quantity === null || this.discount === null) {
				alert("Sva polja moraju biti popunjena!");
				return;
			}
			
			axios
			.post('/admin/createPromoCode', {
				startDate: this.startDate,
				endDate: this.endDate,
				quantity: this.quantity,
				discountPercentage: this.discount
			})
			.then(response => {
                if(response.data){
                    alert("Promo kod je uspešno kreiran.");
                    this.$router.push('/mainPage');
                }
            });
		
			
    	}
	},
	mounted (){
		
		},
	template: 
    `
        <div>
            <h1>Definisanje promo kodova</h1>
            <table>
                <tr>
                    <td><label for="startDate">Start date:</label></td>
                    <td><input type="date" v-model="startDate" id="startDate"></td>
                </tr>
                
                <tr>
                    <td><label for="endDate">End date:</label></td>
                    <td><input type="date" v-model="endDate" id="endDate"></td>
                </tr>
                
                <tr>
                    <td><label for="quantity">Quantity:</label></td>
                    <td><input type="number" v-model="quantity" id="quantity"></td>
                </tr>
                
                <tr>
                    <td><label for="discount">Discount (%):</label></td>
                    <td><input type="number" step="any" v-model="discount" id="discount"></td>
                </tr>
                
                <tr>
                    <td></td>
                    <td><button v-on:click="createPromoCode()">Create</button></td>
                </tr>
            </table>
        </div>
	`
})