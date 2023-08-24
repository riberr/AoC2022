test_input = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
"""

defmodule Day03.Part1 do
  def find_common(rucksack) do
    splits =
      rucksack
      |> (fn r -> String.split_at(r, (String.length(rucksack) / 2) |> trunc()) end).()
      |> String.to_charlist() |> MapSet.new()

    # |> (fn r -> String.intersection(elem(r, 0), elem(r, 1)) end).()
    # IO.inspect(rucksack)
    char = MapSet.intersection(elem(splits, 0), elem(splits, 1)) |> MapSet.to_list() |> hd

    IO.inspect(char)
  end
end

ans1 =
  test_input
  |> String.split("\r\n", trim: true)
  |> Enum.map(&Day03.Part1.find_common/1)

# |> Enum.map(fn r -> MapSet.intersection(elem(r, 0), elem(r, 1)) end)

IO.puts("part1: #{inspect(ans1)}")
