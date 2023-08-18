test_input = """
A Y
B X
C Z
"""

# rock = A, X
# paper = B, Y
# scissor = C, Z

# setup
{:ok, input} = File.read("input")

# part1
defmodule Day01.Part1 do
  def outcome_score(round) do
    case round do
      round when round in ["A Z", "B X", "C Y"] -> 0
      round when round in ["A X", "B Y", "C Z"] -> 3
      round when round in ["A Y", "B Z", "C X"] -> 6
    end
  end

  def shape_score(round) do
    case String.at(round, 2) do
      "X" -> 1
      "Y" -> 2
      "Z" -> 3
    end
  end
end

ans1 =
  input
  |> String.split("\r\n", trim: true)
  |> Enum.map(fn round -> Day01.Part1.outcome_score(round) + Day01.Part1.shape_score(round) end)
  |> Enum.sum()

IO.puts("part1: #{inspect(ans1)}")

# part2

# x = lose
# y = draw
# z = win

defmodule Day01.Part2 do
  def score(round) do
    case round do
      round when round in ["B X"] -> 1
      round when round in ["C X"] -> 2
      round when round in ["A X"] -> 3
      round when round in ["A Y"] -> 4
      round when round in ["B Y"] -> 5
      round when round in ["C Y"] -> 6
      round when round in ["C Z"] -> 7
      round when round in ["A Z"] -> 8
      round when round in ["B Z"] -> 9
    end
  end
end

ans2 =
  input
  |> String.split("\r\n", trim: true)
  |> Enum.map(fn round -> Day01.Part2.score(round) end)
  |> Enum.sum()

IO.puts("part2: #{inspect(ans2)}")
