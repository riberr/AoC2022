package main

import (
	"bufio"
	"log"
	"os"
	"strconv"
	"strings"
)

func fullyContains(l1 int, l2 int, r1 int, r2 int) bool {
	if r1 >= l1 && r2 <= l2 {
		return true
	} else if l1 >= r1 && l2 <= r2 {
		return true
	} else {
		return false
	}

}

func main() {
	input, err := os.Open("./input")
	if err != nil {
		log.Fatal(err)
	}
	defer input.Close()

	answer := 0
	scanner := bufio.NewScanner(input)
	for scanner.Scan() {
		pairs := strings.Split(scanner.Text(), ",")

		pairLeft := strings.Split(pairs[0], "-")
		pairRight := strings.Split(pairs[1], "-")
		log.Println(pairLeft)

		l1, _ := strconv.Atoi(pairLeft[0])
		l2, _ := strconv.Atoi(pairLeft[1])
		r1, _ := strconv.Atoi(pairRight[0])
		r2, _ := strconv.Atoi(pairRight[1])

		if fullyContains(l1, l2, r1, r2) {
			answer += 1
		}
	}
	log.Println("Answer1: ", answer)
}
