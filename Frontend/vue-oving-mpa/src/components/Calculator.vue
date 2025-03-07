<script>
export default {
  data() {
    return {
      display: '',
      operator: '',
      firstNumber: 0,
      secondNumber: 0,
      result: 0,
      histories:[],
      apiUrl: "http://localhost:8088/api/calculator/calculate",
      historyUrl: "http://localhost:8088/api/calculator/history",
      loginUrl: "http://localhost:8088/api/calculator/login",
      operands: [],
      operators: [],
    }
  },
  created() {
    this.fetchHistory();
  },
  methods: {
    handleClick(value) {
        this.result += value;
    },
    handleClear() {
      this.result = '';
    },
    handleClearEntry() {
      this.handleClear();
      this.histories = [];
    },
    handleOperatorClick(operator) {
      this.result += operator;
    },
    handleInput(event) {
      this.result = event.target.value;
    },
    async calculate() {
      if (this.result !== '') {
      }

      const token = localStorage.getItem('jwtToken');
      try {
        const response = await fetch(this.apiUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
          body: JSON.stringify({ expression: this.result }),
        });
        if (response.ok) {

          const data = await response.json();
          this.fetchHistory()
          this.result = data.result;
        } else if (response.status === 401) {
          await this.refreshToken();
          this.calculate();
        } else {
          console.error('Calculation failed:', response.status);
        }
      } catch (error) {
        console.error('Error:', error);
      }
    },
    async fetchHistory() {
      const token = localStorage.getItem('jwtToken');
      try {
        const response = await fetch(this.historyUrl, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          this.histories = data.map((item) => `${item.expression} = ${item.result}`);
        } else if (response.status === 401) {
          await this.refreshToken();
          this.fetchHistory(); // Retry after refreshing token
        } else {
          console.error('History fetch failed:', response.status);
        }
      } catch (error) {
        console.error('Error fetching history:', error);
      }
    },
    async refreshToken() {
      try {
        const response = await fetch(this.loginUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ username: 'testuser', password: 'password123' }), // Replace with real credentials
        });

        if (response.ok) {
          const token = await response.text();
          localStorage.setItem('jwtToken', token);
        } else {
          this.$router.push('/login'); // Redirect to login if refresh fails
        }
      } catch (error) {
        console.error('Token refresh failed:', error);
        this.$router.push('/login');
      }
    },
  },
};
</script>

<template>
  <div id = "calculator">
    <div class="calculator">
      <input type="text" class = "result" :value="result" @input="handleInput">
      <div class="buttons">
        <button class="number" id="button-7"
                @click="handleClick('7')">7</button>
        <button class="number" id="button-8"
                @click="handleClick('8')">8</button>
        <button class="number" id="button-9"
                @click="handleClick('9')">9</button>
        <button class="operator" id="divide"
                @click="handleOperatorClick('/')">/</button>

        <button class="number" id="button-4"
                @click="handleClick('4')">4</button>
        <button class="number" id="button-5"
                @click="handleClick('5')">5</button>
        <button class="number" id="button-6"
                @click="handleClick('6')">6</button>
        <button class="operator" id="multiply"
                @click="handleOperatorClick('*')">*</button>

        <button class="number" id="button-1"
                @click="handleClick('1')">1</button>
        <button class="number" id="button-2"
                @click="handleClick('2')">2</button>
        <button class="number" id="button-3"
                @click="handleClick('3')">3</button>
        <button class="operator" id="subtract"
                @click="handleOperatorClick('-')">-</button>

        <button class="number" id="button-0"
                @click="handleClick('0')">0</button>
        <button class="number" id="."
                @click="handleClick('.')">.</button>
        <button class="number" id="00"
                @click="handleClick('00')">00</button>

        <button class="operator" id="add"
                @click="handleOperatorClick('+')">+</button>

        <button class="clear" id="clear"
                @click="handleClear">C</button>
        <button class="clear" id="clearAll"
                @click="handleClearEntry">CE</button>
        <button class="equal" id="equal"
                @click="calculate()">=</button>
      </div>
    </div>
  </div>
  <div id = "calculatorHistory">
    <div class="calculatorHistory">
      <h2>History</h2>
      <ul>
        <li v-for="history in histories" :key="history">{{ history }}</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.calculator {
  width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.result {
  width: 90%;
  padding: 10px;
  font-size: 20px;
  text-align: right;
  margin-bottom: 10px;
}

.buttons {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 10px;
}
button {
  padding: 15px;
  font-size: 18px;
  cursor: pointer;
  border: none;
  outline: none;
}

.number, operator {
  background-color: #f0f0f0;
}
.clear, .equal {
  background-color: #c42334;
  color: #fff;
}

button:hover {
  background-color: #ffffff;
}

.equal {
  grid-column: span 2;
}

.calculatorHistory {
  width: 300px;
  height: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: auto;
}
</style>
