Vue.component('customerCommentsView', {
	data: function() {
		return {
			comments: null
		}
	},
	mounted (){
        axios.get("/customer/getAprovedComments",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.comments = response.data; 
        })
		},
	template: 
    `
        <div>
            <h1>Pregled odobrenih komentara</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Komentar</th>
                        <th>Ocena</th>
                        <th>Kupac(ime i prezime - korisničko ime)</th>
                        <th>Naziv sportskog objekta</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="comment in comments">
                        <td>{{comment.text}}</td>
                        <td>{{comment.score}}</td>
                        <td>{{comment.customer.name}} {{comment.customer.surname}} - {{comment.customer.username}}</td>
                        <td>{{comment.sportFacility.name}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})