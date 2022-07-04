Vue.component('home', {
	data: function() {
		return {};
	},
	
	template: `
	<div>
		<login class="center"/> <br/>
		<h2>Naše teretane:</h2>
		<facilitiesView />
	</div>
`
})