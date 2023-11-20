namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Room
    /// </summary>
    public class Room
    {
        /// <summary>
        /// Room's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Room's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Room's Icon
        /// </summary>
        public string? Icon { get; set; }

        /// <summary>
        /// User's Id
        /// </summary>
        public int UserId { get; set; }
    }
}
