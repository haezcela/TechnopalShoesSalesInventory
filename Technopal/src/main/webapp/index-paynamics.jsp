<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pay with GCash via Paynamics</title>
</head>
<body>
    <h1>Pay with GCash</h1>
    <form id="payment-form">
        <label for="customer_name">Name:</label>
        <input type="text" id="customer_name" name="customer_name" required><br><br>

        <label for="customer_email">Email:</label>
        <input type="email" id="customer_email" name="customer_email" required><br><br>

        <label for="amount">Amount:</label>
        <input type="text" id="amount" name="amount" required><br><br>

        <button type="submit">Pay with GCash</button>
    </form>

    <div id="payment-result"></div>
</body>

<script>
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

console.log(generateUUID());
</script>

<script>
	document.getElementById('payment-form').addEventListener('submit', function(event) {
	    event.preventDefault();
	
	    // Get form data
	    const customerName = document.getElementById('customer_name').value;
	    const customerEmail = document.getElementById('customer_email').value;
	    const amount = document.getElementById('amount').value;
	
	    // Data to send to Paynamics
	    const paymentData = {
	        customer_name: customerName,
	        customer_email: customerEmail,
	        amount: amount,
	        currency: 'PHP',
	        payment_method: 'GCASH',  // Specify GCash as the payment method
	        merchant_id: '00000008082498E6FD39',  // Replace with your actual Merchant ID
	        request_id: '645F2583D7F7A057590BF6B22E651F68',  // Generate a unique request ID
	        signature: '',  // Generate signature (server-side recommended)
	        redirect_url: 'https://mytechnopal.com', // Your success redirect URL
	        cancel_url: 'https://google.com',    // Your cancel redirect URL
	        username: 'negrosPGNO*%#',
	        password: 's2NbycAU#0!s'
	    };
	
	    // Example of creating a signature (simplified)
	    // const signature = createSignature(paymentData, 'YOUR_SECRET_KEY');
	    // paymentData.signature = signature;
	
	    // Simulate sending data to Paynamics API
	    fetch('https://api.payserv.net/v1/rpf/transactions/rpf', {  // Use Paynamics API URL
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify(paymentData),
	    })
	    .then(response => response.json())
	    .then(data => {
	        if (data.status === 'success') {
	            document.getElementById('payment-result').innerText = 'Redirecting to GCash...';
	            window.location.href = data.redirect_url;
	        } else {
	            document.getElementById('payment-result').innerText = 'Payment Failed: ' + data.message;
	        }
	    })
	    .catch(error => {
	        document.getElementById('payment-result').innerText = 'Error: ' + error.message;
	    });
	});

	// Example of a signature creation function
	function createSignature(data, secretKey) {
	    // Implement your signature creation logic here
	    return 'signature';
	}
</script>

</html>